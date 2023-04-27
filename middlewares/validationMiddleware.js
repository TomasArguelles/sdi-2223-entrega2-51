const {check, query} = require("express-validator");

module.exports = {
    /**
     * Valida los campos del formulario de dar de alta una nueva oferta.
     *
     * - Los campos titulo, descripcion, precio no pueden estar vacios
     * - El precio debe ser un numero
     * - El precio debe ser mayor que 0
     * - El titulo debe tener al menos 5 caracteres y como maximo 50
     * - La descripcion debe tener al menos 20 caracteres y como maximo 500
     */
    validateAddOfferFields: function () {
        return [
            check('title', 'El título es obligatorio').notEmpty(),
            check('description', 'La descripción es obligatoria').notEmpty(),
            check('price', 'El precio es obligatorio').notEmpty(),
            check('price', 'El precio debe ser un número').isNumeric(),
            check('price', 'El precio debe ser mayor que 0').isFloat({min: 0}),
            check('title', 'El título debe tener como máximo 50 caracteres').isLength({max: 50}),
            check('description', 'La descripción debe tener como máximo 500 caracteres').isLength({max: 500})
        ]
    },

    /**
     * Valida que el id de la oferta no este vacio.
     *
     * @returns {[ValidationChain]}
     */
    validateRemoveOfferFields: function () {
        return [
            query('offerId', 'El id de la oferta es obligatorio').trim().notEmpty(),
        ]
    }
}