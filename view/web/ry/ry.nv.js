ry = function() {
	var ry = {
	    version: "1.0"
	};

	// Adapt from an array of array to nv.d3 Model 
	// Applies to: pie()
	// The input should be: 
	// 	Ignored    Ignored  
	// 	GroupA           n   
	// 	GroupB           n
	// 	...
	// Return:
	// 	[[{key: "Serie1", y: n}, {key: "Serie2", y: 2}, ...]]
   	ry.piemodel = function(data) {
   		var ret = data.slice(1).map(function(d, i) {
   			return { key:d[0], y:d[1] }
   		});
   		
   		return [ret];
   	}
     		
	// Adapt from an array of array to nv.d3 Model 
	// Applies to: bar(), barh()
	// The array[][] should de: 
	// 	Ignored    Serie1  Serie2  ...
	// 	GroupA          n       n  ...       
	// 	GroupB          n       n  ... 
	// 	...
	// Return:[{"key": "Serie1",
	// 	     "values": [      
	// 	        { x : "GroupA", y : n, series: 0 },
	// 	        { x : "GroupB", y : n, series: 0 },
	// 	        ...]},
	// 	      {"key": "Serie2",
	// 		   "values": [      
	// 			 { x : "GroupA", y : n, series: 1 },
	// 			 { x : "GroupB", y : n, series: 1 },
	// 			 ...]}
	// 	   }]
  	ry.barmodel = function(data) {
  		var nserie = data[0].length;
  		var ngroup = data.length;
  		var ret = [];
  		
  		for (var s = 1; s < nserie; s++) {
  			var serie = data[0][s];
  			var values = [];
  			for (var g = 1; g < ngroup; g++) {
  				var value = { x: data[g][0], y: data[g][s], series: (s-1) };
  				values.push(value);
  			}
  			ret.push({"key": serie, "values": values});	
  		}     		
  		return ret;
  	}
  	
  	//transpose a matrix t
  	ry.transpose = function(t) {
  	    if (!t.length) {
  	        return [];
  	    }
  	    
  	    var tlen = t.length;
        var dlen = t[0].length;
        var newA = new Array(dlen);
  	    for (var i = 0; i < dlen; ++i) {
  	        newA[i] = [];
  	        for (var j = 0, l = tlen; j < l; j++) {
  	            newA[i][j] = t[j][i];
  	        }
  	    }

  	    return newA;
  	}
  	
  	//make a new matrix with the cols indexes of m 
  	ry.matrix = function(m, cols) {
  	    var mlen = m.length,
  	        clen = cols.length,
  	        ret = [];
  	    
  	    for(var i = 0; i < mlen; i++) {
  	        ret[i] = [];
  	        for(var j = 0; j < clen; j++) {
  	            ret[i][j] = m[i][cols[j]];
  	        }
  	    }
  	    return ret;
  	}

  	
  	ry.bar = function(select, data, fchart) {
  		nv.addGraph(function() {
  			var chart;
  			chart = nv.models.multiBarChart()
  			
  			chart.multibar.hideable(true);
  			chart.xAxis.showMaxMin(false);
  			chart.yAxis.showMaxMin(false);
  			chart.yAxis.tickFormat(ry.format(',.2f'));
  			
  			//chart.xAxis.tickFormat(d3.format(',.2f'));
  			//.width(300)
  			//.height(300)
  			//.barColor(d3.scale.category20().range());

  			if (fchart)
  				  fchart(chart);

  			d3.select(select)
  			  	.style('width', chart.width() + 'px')
  			  	.style('height', chart.height() + 'px')		      
  			  	.attr('width', chart.width() + 'px')
  			  	.attr('height', chart.height() + 'px')
  			  	.datum(data)
  				.transition().duration(250).call(chart);

  			
  			return chart;
  		});
  	}

  	ry.pie = function(select, data, fchart) {
  		nv.addGraph(function() {			
  			var chart = nv.models.pieChart()
  				.valueFormat(ry.format(',.2f'))
  				.showLegend(false)
  				.x(function(d) { return d.key})
  				.values(function(d) { return d })
  				.margin({top:0, left:0, bottom:0, right:0})
  				.donut(true);
  			
  			//chart.yAxis.tickFormat(d3.format('.,2f'));
  			//chart.xAxis.tickFormat(d3.format('.,2f'));

  			//.y(function(d) { return d.value })
  			//.width(300)
  			//.height(300)
  			//.labelThreshold(.08)
  			//.showLabels(false)
  			//.pie.donutLabelsOutside(true);

  			if (fchart)
  				  fchart(chart);

  			d3.select(select)
  				.style('width', chart.width() + 'px')
  				.style('height', chart.height() + 'px')
  				.datum(data)
  				.transition()
  				.duration(250)
  				.call(chart);
  			return chart;
  		});
  	}
  	
  	ry.barh = function(select, data, fchart) {
  		var chart;
  		nv.addGraph(function() {
  			chart = nv.models.multiBarHorizontalChart()
  				.valueFormat(ry.format(',.2f'))
  				.tooltips(true)
  				.showControls(true)
  		        .margin({top: 20, right: 40, bottom: 20, left: 80})
  		  
  			chart.xAxis.showMaxMin(false);
  			chart.yAxis.showMaxMin(false);
  		    chart.yAxis.tickFormat(ry.format(',.2f'));
  			//chart.xAxis.tickFormat(d3.format(',.2f'));

  			//.width(300)
  			//.height(300)
  		    //.x(function(d) { return d.label })
  		    //.y(function(d) { return d.value })
  		    //.showValues(false)
  		    //.barColor(d3.scale.category20().range())
  		    //chart.yAxis.tickFormat(d3.format(',.2f'));
  	
 			if (fchart)
				fchart(chart);

			d3.select(select)
  			  .style('width', chart.width() + 'px')
  			  .style('height', chart.height()  + 'px')		      
  		      .datum(data)
  	          .transition().duration(250)
  		      .call(chart);
			
			nv.utils.windowResize(chart.update);
			
			return chart;
  		});
  	}

  	//draw an HTML table inside select elements 
  	ry.table = function(select, data, format) {
  		var t = [];
  		
  		var fmt = format || ry.format(",.2f");
  		
  		t.push("<thead><tr>")
  		data[0].forEach(function (cell) { t.push("<th>", cell, "</th>") } )
  		t.push("</thead><tbody>")

   		data.slice(1).forEach(function (row) {
   			t.push("<tr>");
  	 		row.forEach(function (cell, i) {
  	 				if (i == 0)
  	 					t.push("<td>", cell, "</td>");
  	 				else {
  	 					t.push("<td class=data>", fmt(cell), "</td>");
  	 				}
  	 			} );
  	 		t.push("<tr>");
   		})
  		t.push("</tbody>");

  		var elements = document.querySelectorAll(select);
  		var html = t.join("");
  		for (var i = 0; i < elements.length; i++) {
	  		elements[i].innerHTML = html;
  		}
  	}
  	
  	//parse innerHTML of select, and replace ${expr} with data.expr
  	ry.curly = function(select, data) {
  		var elements = document.querySelectorAll(select);
  		
   		var resolv = function (key, data) {
   			var fn = new Function("obj", "with (obj) { try {return "+key+";} catch (e) { console.error('curly: fail evaluating expression: ("+key+") : ' + e); return '"+key+"' } }");
   			
  		    return fn(data)
  		}
  		
  		for (var i = 0; i < elements.length; i++) {
			var e = elements[i];
  			var s = e.innerHTML.replace(/\$\{(.*?)\}/g, function(match, key){
  				return resolv(key, data) || match;
  			});
   			e.innerHTML = s;
  		}
  	}
  	
	//parse a string with csv format, and return an array of array.
	//parse try coerce each value to float, if fail leave as string.
	//based on d3_dsv
	function ry_dsv(delimiter) {
	   var reFormat = new RegExp('["' + delimiter + "\n]"), delimiterCode = delimiter.charCodeAt(0);
	   var dsv = {};
	   
	   dsv.parse = function(text, f) {
	     var EOL = {}, EOF = {}, rows = [], N = text.length, I = 0, n = 0, t, eol;
	     
	     function token() {
	       if (I >= N) return EOF;
	       if (eol) return eol = false, EOL;
	       var j = I;
	       if (text.charCodeAt(j) === 34) {
	         var i = j;
	         while (i++ < N) {
	           if (text.charCodeAt(i) === 34) {
	             if (text.charCodeAt(i + 1) !== 34) break;
	             ++i;
	           }
	         }
	         I = i + 2;
	         var c = text.charCodeAt(i + 1);
	         if (c === 13) {
	           eol = true;
	           if (text.charCodeAt(i + 2) === 10) ++I;
	         } else if (c === 10) {
	           eol = true;
	         }
	         return text.substring(j + 1, i).replace(/""/g, '"');
	       }
	       while (I < N) {
	         var c = text.charCodeAt(I++), k = 1;
	         if (c === 10) eol = true; else if (c === 13) {
	           eol = true;
	           if (text.charCodeAt(I) === 10) ++I, ++k;
	         } else if (c !== delimiterCode) continue;
	         return text.substring(j, I - k);
	       }
	       return text.substring(j);
	     }
	     
	     while ((t = token()) !== EOF) {
	       var a = [];
	       while (t !== EOL && t !== EOF) {
	         var fl = dsv.toFloat(t);
	         a.push(fl == fl ? fl : t);
	         t = token();
	       }
	       if (f && !(a = f(a, n++))) continue;
	       rows.push(a);
	     }
	     return rows;
	   };
	   
	   dsv.toFloat = function (value) {
	      if(/^\-?([0-9]+(\.[0-9]+)?|Infinity)$/
	          .test(value))
	          return Number(value);
	      return NaN;
	   }
	   
	   return dsv;
	}
	
	ry.csv = ry_dsv(","); //, "text/csv");	
	ry.tsv = ry_dsv("	"); //, "text/tab-separated-values");
	
	ry.xhr = function(url, mime, callback) {
		var req = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");
		var hndChange = function() {
			if (this.readyState == 4) {
				var s = req.status;
				callback(!s && req.responseText || s >= 200 && s < 300 || s === 304 ? req : null);
			}
		}

		if (callback) {
			req.onreadystatechange = hndChange;
		}

		if (mime != null && req.overrideMimeType)
			req.overrideMimeType(mime);

		req.open('GET', url, !(!callback));
		req.send(null);

		return req.responseText;
	}
	
	ry.json = function(url) {
		var json = ry.xhr(url, "application/json");
		return JSON.parse(json);
	}
	
	//swap decimal and thousands separators. (es locale)
	ry.format = function(fmt) {
		return function(v) {
			var ret = d3.format(fmt)(v);
			if (ret === "NaN")
				return v;
			
			ret = ret.replace(",", "_","g")
				.replace(".", ",","g")
				.replace("_", ".","g");
			
			return ret;
		}
	}

	return ry;
}();
