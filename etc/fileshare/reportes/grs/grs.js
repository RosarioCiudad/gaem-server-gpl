Math.sum = function () {
    var sum = 0;
    for (var i = 0, j = arguments.length; i < j; i++) {
        sum += arguments[i];
    }
    return sum;
}

//una forma facil para buscar items por id en un array
Array.prototype.rowid = function(id) {
	for (var i=0; i < this.length; i++) {
		var row = this[i];
        if (row && row.id == id) {
            return row;
        }
    }
};

