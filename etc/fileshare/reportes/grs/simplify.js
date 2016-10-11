/*
 (c) 2013, Vladimir Agafonkin
 Simplify.js, a high-performance JS polyline simplification library
 mourner.github.io/simplify-js
*/

(function () { 
	var simplify = {};

// to suit your point format, run search/replace for '.lon' and '.lat';
// for 3D version, see 3d branch (configurability would draw significant performance overhead)

// square distance between 2 points
simplify.getSqDist = function (p1, p2) {

    var dx = p1.lon - p2.lon,
        dy = p1.lat - p2.lat;

    return dx * dx + dy * dy;
}

// square distance from a point to a segment
simplify.getSqSegDist = function (p, p1, p2) {

    var x = p1.lon,
        y = p1.lat,
        dx = p2.lon - x,
        dy = p2.lat - y;

    if (dx !== 0 || dy !== 0) {

        var t = ((p.lon - x) * dx + (p.lat - y) * dy) / (dx * dx + dy * dy);

        if (t > 1) {
            x = p2.lon;
            y = p2.lat;

        } else if (t > 0) {
            x += dx * t;
            y += dy * t;
        }
    }

    dx = p.lon - x;
    dy = p.lat - y;

    return dx * dx + dy * dy;
}
// rest of the code doesn't care about point format

// basic distance-based simplification
simplify.simplifyRadialDist = function (points, sqTolerance) {

    var prevPoint = points[0],
        newPoints = [prevPoint],
        point;

    for (var i = 1, len = points.length; i < len; i++) {
        point = points[i];

        if (simplify.getSqDist(point, prevPoint) > sqTolerance) {
            newPoints.push(point);
            prevPoint = point;
        }
    }

    if (prevPoint !== point) {
        newPoints.push(point);
    }

    return newPoints;
}

// simplification using optimized Douglas-Peucker algorithm with recursion elimination
simplify.simplifyDouglasPeucker = function (points, sqTolerance) {

    var len = points.length,
        MarkerArray = typeof Uint8Array !== 'undefined' ? Uint8Array : Array,
        markers = new MarkerArray(len),
        first = 0,
        last = len - 1,
        stack = [],
        newPoints = [],
        i, maxSqDist, sqDist, index;

    markers[first] = markers[last] = 1;

    while (last) {

        maxSqDist = 0;

        for (i = first + 1; i < last; i++) {
            sqDist = simplify.getSqSegDist(points[i], points[first], points[last]);

            if (sqDist > maxSqDist) {
                index = i;
                maxSqDist = sqDist;
            }
        }

        if (maxSqDist > sqTolerance) {
            markers[index] = 1;
            stack.push(first, index, index, last);
        }

        last = stack.pop();
        first = stack.pop();
    }

    for (i = 0; i < len; i++) {
        if (markers[i]) {
            newPoints.push(points[i]);
        }
    }

    return newPoints;
}

// both algorithms combined for awesome performance
simplify.simplify = function (points, tolerance, highestQuality) {

    var sqTolerance = tolerance !== undefined ? tolerance * tolerance : 1;

    points = highestQuality ? points : simplify.simplifyRadialDist(points, sqTolerance);
    points = simplify.simplifyDouglasPeucker(points, sqTolerance);

    return points;
}


return simplify;

}());
