const {ObjectId} = require("mongodb");
const {formatDate} = require("../../util/dateUtils");
module.exports = function (app, usersRepository, offersRepository) {
    app.post('/api/v1.0/users/login', function (req, res) {
        try {
            let securePassword = app.get("crypto").createHmac('sha256', app.get('clave'))
                .update(req.body.password).digest('hex');
            let filter = {email: req.body.email, password: securePassword};
            let options = {};
            usersRepository.findUser(filter, options).then(user => {
                if (user == null) {
                    res.status(401); // Unauthorized
                    res.json({
                        message: "usuario no autorizado",
                        authenticated: false
                    });
                } else {
                    let token = app.get('jwt').sign({
                        user: user.email, time: Date.now() / 1000
                    }, "secreto");
                    res.status(200);
                    res.json({
                        message: "usuario autorizado",
                        authenticated: true,
                        token: token
                    });
                }
            }).catch(error => {
                res.status(401);
                res.json({
                    message: "Se ha producido un error al verificar credenciales",
                    authenticated: false
                });
            });
        } catch (e) {
            res.status(500);
            res.json({
                message: "Se ha producido un error al verificar credenciales",
                authenticated: false
            });
        }
    });

    app.get("/api/v1.0/offers", function (req, res) {
        let filter = {};
        let options = {};
        let userA = res.user; // email
        offersRepository.getOffers(filter, options).then(offers => {
            let userB = offers[0].seller;
            if (userA !== userB) {  // offers de los usuarios diferentes al usuario en sesión
                res.status(200);
                // Mostrar la hora en formato dd/mm/yyyy hh:mm
                let formatedOffers = offers?.map(offer => {
                    offer.date = formatDate(offer.date);
                    return offer;
                });
                res.send({offers: formatedOffers});
            }
        }).catch(() => {
            res.status(500);
            res.json({error: "Se ha producido un error al obtener las ofertas."})
        });
    });

    app.post('/api/v1.0/messages/add', function (req, res) {
        try {
            let msg = {
                idOffer: req.body.offerId,
                idSender: req.session.email,
                idReceiver: req.body.receiver,
                leido: req.session.leido,
                texto: req.body.texto,
                timestamp: Date.now()
            }
            let filter = {sender:msg.idSender,receiver: msg.idReceiver,offer: msg.idOffer};
            conversationsRepository.findConversation(filter, async function (conversation) {
                try {
                    if (conversation) {
                        messagesRepository.addMessage(msg, function (messageId) {
                            if (messageId === null) {
                                res.status(409);
                                res.json({error: "No se ha podido añadir el mensaje."});
                            } else {
                                res.status(201);
                                res.json({
                                    message: "Mensaje añadido correctamente.",
                                    _id: messageId
                                });
                            }
                        });
                    } else {
                        let conv = {
                            sender: msg.idSender,
                            receiver: msg.idReceiver,
                            oferta: req.body.idOffer
                        };
                        conversationsRepository.addConversation(conv, async function (conversationId) {
                            if (conversationId === null) {
                                res.status(409);
                                res.json({error: "No se ha podido crear la conversación."});
                            } else {
                                messagesRepository.addMessage(msg, { conversationId: conversationId }, function (messageId) {
                                    if (messageId === null) {
                                        res.status(409);
                                        res.json({error: "No se ha podido añadir el mensaje."});
                                    } else {
                                        res.status(201);
                                        res.json({
                                            message: "Mensaje añadido correctamente.",
                                            _id: messageId
                                        });
                                    }
                                });
                            }
                        });
                    }
                } catch (error) {
                    res.status(500);
                    res.json({error: "Se ha producido un error al intentar añadir el mensaje: " + error});
                }
            });
        } catch (e) {
            res.status(500);
            res.json({error: "Se ha producido un error al intentar añadir el mensaje: " + e});
        }
    });

    app.get('/api/v1.0/messages/:offerId', function (req, res) {
        const userId = req.user.id;
        const offerId = req.params.offerId;
        let filter = {sender:userId,offer: offerId};
        let options = {};
        conversationsRepository.getConversations(filter, options).then(songs => {
            res.status(200);
            res.send({songs: songs})
        }).catch(error => {
            res.status(500);
            res.json({error: "Se ha producido un error al recuperar las ofertas."})
        });
    });
}