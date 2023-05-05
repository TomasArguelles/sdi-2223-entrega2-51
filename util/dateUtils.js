module.exports = {
    /**
     * Formatea una fecha en formato dd/mm/yyyy hh:mm:ss
     * @param date
     * @returns {string}
     */
    formatDate: function (date) {
        return date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear() + " " +
            date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    }
}