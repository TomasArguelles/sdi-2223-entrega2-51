const {ObjectId} = require("mongodb");
module.exports = function (app, commentsRepository) {
    app.post('/comments/:song_id', function (req, res) {
        if (req.session.user == null) {
            res.send("Error de identificación");
            return;
        }
        let comment = {
            author: req.session.user,
            text: req.body.text,
            song_id: ObjectId(req.params.song_id),
        }
        if (typeof req.body.text === 'undefinded' || req.body.text === null || req.body.text.trim().length === 0)
            res.send("El comentario no debe estar vacio")
        else {
            commentsRepository.insertComment(comment, function (songId) {
                if (songId === null) {
                    res.send("Error al insertar la canción");
                } else {
                    res.redirect("/songs/"+req.params.song_id)
                }
            });
        }
    });
}