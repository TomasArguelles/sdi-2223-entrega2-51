const {ObjectId} = require("mongodb");
const {formatDate} = require("../../util/dateUtils");
module.exports = function (app, usersRepository, offersRepository,conversationsRepository,messagesRepository) {
    app.post('/api/v1.0/users/login', function (req, res) {
        try {
            let securePassword = app.get("crypto").createHmac('sha256', app.get('clave'))
                .update(req.body.password).digest('hex');
            let filter = {
                email: req.body.email,
                password: securePassword
            };
            let options = {};
            usersRepository.findUser(filter, options).then(user => {
                if (user == null) {
                    res.status(401); // Unauthorized
                    res.json({
                        message: "usuario no autorizado",
                        authenticated: false
                    });
                } else {
                    let token = app.get('jwt').sign(
                        {user: user.email, time: Date.now() / 1000}, "secreto");
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
            let userB = offers.seller;
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

        let idConver=ObjectId(req.body.idConver);

        let idBuyer = res.user;
        let tituloOffer = req.body.offerTitle;
        let idSeller = req.body.idSeller;
        let idOffer = req.body.idOffer;
        let sender = res.user;
        let receiver;
        let message = req.body.texto;
        let leido = false;
        let date = Date.now();

        if (res.user === idSeller ){
            receiver = idBuyer;
        }else{
            receiver = idSeller;
        }

        try {

            //Comprueno si existe esa conversacion
            let filter = {
                _id : idConver
            };
            let options = {};

            conversationsRepository.findConversation(filter, options).then(conversation=> {
                try {
                    if (conversation) {
                        if (res.user === conversation.seller ){
                            receiver = conversation.buyer;
                        }else{
                            receiver = conversation.seller;
                        }
                        let msg = {
                            idConver:idConver,
                            idSender:sender,
                            idReceiver:receiver,
                            leido:leido,
                            texto:message,
                            timestamp:date
                        }
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
                            buyer:idBuyer,
                            seller: idSeller,
                            oferta: idOffer,
                            tituloOffer: tituloOffer
                        };
                        conversationsRepository.addConversation(conv, async function (conversationId) {
                            if (conversationId === null) {
                                res.status(409);
                                res.json({error: "No se ha podido crear la conversación."});
                            } else {
                                let msg = {
                                    idConver:conversationId,
                                    idSender:sender,
                                    idReceiver:receiver,
                                    leido:leido,
                                    texto:message,
                                    timestamp:date
                                }
                                messagesRepository.addMessage(msg,  function (messageId) {
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


/**
    app.post('/api/v1.0/messages/add', function (req, res) {



        try {
            let msg = {
                idOffer: req.body.idOffer,
                idSender: res.user,
                idReceiver: req.body.idReceiver,
                leido: false,
                texto: req.body.texto,
                timestamp: Date.now()
            }

            let filter = {sender:msg.idSender,receiver: msg.idReceiver,oferta: msg.idOffer};

            let options = {};
            conversationsRepository.findConversation(filter, options).then(conversation=> {
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
                            oferta: msg.idOffer,
                            tituloOferta: req.body.offerTitle
                        };
                        conversationsRepository.addConversation(conv, async function (conversationId) {
                            if (conversationId === null) {
                                res.status(409);
                                res.json({error: "No se ha podido crear la conversación."});
                            } else {
                                messagesRepository.addMessage(msg,  function (messageId) {
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
**/
    app.get('/api/v1.0/messages/:id', function (req, res) {

        let filter = {idConver:ObjectId(req.params.id)};
        console.log(filter);
        let options = {};
        messagesRepository.getMessages(filter, options).then(messages => {
            res.status(200);
            res.send({messages: messages})
        }).catch(error => {
            res.status(500);
            res.json({error: "Se ha producido un error al recuperar las ofertas."})
        });
    });

    app.get('/api/v1.0/conversations/', function (req, res) {
        let user = res.user;
        let filterUsuarioVendedor = {buyer: user};
        let filterUsuarioInteresado = {seller: user};
        let options = {};
        let todas=[]
        conversationsRepository.getConversations(filterUsuarioVendedor, options).then(convs => {
            convs.forEach(c => {
                    todas.push(c);
            })
            conversationsRepository.getConversations(filterUsuarioInteresado, options).then(convs => {
                convs.forEach(c => {
                    todas.push(c);
                })
                res.status(200);
                res.send({
                    listadoConversaciones: todas
                })
            })

        });

    });


    app.put('/api/v1.0/messages/:id', function (req, res) {
        try {
            let msgId = ObjectId(req.params.id);
            let filter = {_id: msgId};
            //Si la _id NO no existe, no crea un nuevo documento.
            const options = {upsert: false};
            let actu;

            messagesRepository.markAsReadMessage(filter, options).then(result => {
                actu=result;
            });


        } catch (e) {
            res.status(500);
            res.json({error: "Se ha producido un error al intentar modificar la canción: "+ e})
        }
    });
    app.delete('/api/v1.0/messages/:id', function (req, res) {
        try {
            let msgId = ObjectId(req.params.id);
            let filter = {_id: msgId};
            //Si la _id NO no existe, no crea un nuevo documento.
            const options = {upsert: false};
            let actu;

            messagesRepository.deleteMessage(filter, options).then(result => {
                if (result.deletedCount === 0) {
                    res.status(404);
                    res.json({error: "ID inválido o no existe, no se ha borrado el registro."});
                } else {
                    res.status(200);
                    res.send(JSON.stringify(result));
                }
            });


        } catch (e) {
            res.status(500);
            res.json({error: "Se ha producido un error al intentar modificar la canción: "+ e})
        }
    });
}