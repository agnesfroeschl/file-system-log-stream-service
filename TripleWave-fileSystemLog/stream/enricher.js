var stream = require('stream');
var util = require('util');
var _ = require('underscore');
var R2rml = require('../r2rml-js/r2rml.js');
var PropertiesReader = require('properties-reader');
var path = require('path');
var Transform = stream.Transform || require('readable-stream').Transform;
var N3 = require('n3');
var jsonld = require('jsonld');
var dateFormat = require('dateformat');

function EnrichStream(options) {

  var configuration = options.configuration;
  this.mapping = new R2rml(path.resolve(__dirname, '../', configuration.get('transform_folder'), configuration.get('transform_mapping')));

  this.enrich = function(data) {
    var keys = Object.keys(data);

    var mmap = new Map();
    for (var k = 0; k < keys.length; k++) {
      var key = keys[k];
      mmap.set(key, data[key]);
    }
   // console.log("enricher mmap" +JSON.stringify(mmap));

	//console.log("enricher - transform before");
    return this.mapping.transform(mmap);
  };

  // allow use without new
  if (!(this instanceof EnrichStream)) {
    return new EnrichStream(options);
  }

  // init Transform
  Transform.call(this, options);
}
util.inherits(EnrichStream, Transform);


EnrichStream.prototype._transform = function(chunk, enc, cb) {
  var change = chunk;
  change = this.enrich(change);
  var _this = this;
  var result=[];
  var itemsProcessed = 0;
  change.forEach(function (arrayItem) {
   		jsonld.expand(arrayItem, function(err, expanded) {
   			result.push(expanded[0]);
			itemsProcessed++;
			if(itemsProcessed === change.length) {
				itemsProcessed = 0;
 			 	var element = {};
			 	var date = new Date();
			 	var dateString = dateFormat(date, "yyyy-mm-dd'T'HH:MM:ss.l'Z'");
  				element["http://www.w3.org/ns/prov#generatedAtTime"] = date;
 				element["@id"] = "http://Triplewave-stream-transformation/"+dateString; //TODO may add a different id for stream element
 			 	element["@graph"] = result;

  				// console.log("element "+JSON.stringify(element, null, 2));
  			 	_this.push(element);
  			 	cb();
    		}
		});
	
	});
};

exports = module.exports = EnrichStream;
