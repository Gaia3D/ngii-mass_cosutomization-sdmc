
var map = null;
var locationFilter = null;
var dataSearchResultLayer = null;
var selectedAreaLayer = null;
var candidateAreaLayer = null;

var selectedAreaSum = 
{
	"type": "GeometryCollection",
	"geometries": []
}

var candidateArea = null;


L.CustomizedLocationFilter = L.LocationFilter.extend({
	enable: function() {
        if (this._enabled) {
            return;
        }
        
        removeAllSelectedArea();

        // Initialize corners
        var bounds;
        if (this.options.bounds) {
            bounds = this.options.bounds;
        } else {
            bounds = this._map.getBounds();
        }
        this._map.invalidateSize();
        this._nw = bounds.getNorthWest();
        this._ne = bounds.getNorthEast();
        this._sw = bounds.getSouthWest();
        this._se = bounds.getSouthEast();
            

        // Update buttons
        if (this._buttonContainer) {
            this._buttonContainer.addClass("enabled");
        }

        if (this._enableButton) {
            this._enableButton.setText(this.options.enableButton.disableText);
        }

        if (this.options.adjustButton) {
            this._createAdjustButton();
        }
        
        // Draw filter
        this._initialDraw();
        this._draw();

        // Set up map move event listener
        var that = this;
        this._moveHandler = function() {
            that._draw();
        };
        this._map.on("move", this._moveHandler);

        // Add the filter layer to the map
        this._layer.addTo(this._map);
        
        // Zoom out the map if necessary
        var mapBounds = this._map.getBounds();
        bounds = new L.LatLngBounds(this._sw, this._ne).modify(this._map, 10);
        if (!mapBounds.contains(bounds.getSouthWest()) || !mapBounds.contains(bounds.getNorthEast())) {
            this._map.fitBounds(bounds);
        }

        this._enabled = true;
        
        // Fire the enabled event
        this.fire("enabled");
    },
	_adjustToMap: function() {
		var bounds = this.getBounds();
		if(bounds)
		{
			var nw = bounds.getNorthWest();
	        var ne = bounds.getNorthEast();
	        var sw = bounds.getSouthWest();
	        var se = bounds.getSouthEast();

	        var selectedAreaWithRect =
	        {
	        	"type": "Polygon",
	        	"coordinates": [[[nw.lng, nw.lat] , [ne.lng, ne.lat], [se.lng, se.lat], [sw.lng, sw.lat], [nw.lng, nw.lat]]]
	        };
	        
	        addSelectedArea(selectedAreaWithRect);
	        
//	        alert(selectedAreaWithRect.coordinates[0][0] + "\n"
//	        		+ selectedAreaWithRect.coordinates[0][1]  + "\n"
//	        		+ selectedAreaWithRect.coordinates[0][2]  + "\n"
//	        		+ selectedAreaWithRect.coordinates[0][3]  + "\n"
//	        		+ selectedAreaWithRect.coordinates[0][4]);
		}

		this.disable();
	}
});

function prepareMaps() {
    map = L.map('map', {
        crs: L.Proj.CRS.TMS.NgiiMap, // NGII
//        crs: L.Proj.CRS.TMS.VWorld, // VWorld
//        crs: L.Proj.CRS.TMS.Naver, // Naver
        continuousWorld: true,
        worldCopyJump: false,
        zoomControl: true
    });

    
    // NGII
    var baseLayers = {
        'Normal Map': L.Proj.TileLayer.TMS.provider('NgiiMap.Normal').addTo(map)
//        ,'White Map': L.Proj.TileLayer.TMS.provider('NgiiMap.White')
//        ,'Big Font Map': L.Proj.TileLayer.TMS.provider('NgiiMap.LowView')
//        ,'Low Color Map': L.Proj.TileLayer.TMS.provider('NgiiMap.LowColor')
//        ,'English Map': L.Proj.TileLayer.TMS.provider('NgiiMap.English')
    };
    
    
    /*//VWorld
    var baseLayers = {
    	'Hybrid Map': L.Proj.TileLayer.TMS.provider('VWorld.Hybrid').addTo(map),
    	'Satellite Map': L.Proj.TileLayer.TMS.provider('VWorld.Satellite')
    	
    };
    */
    
    /*// Naver
    var baseLayers = {
    	'Cadastral Map': L.Proj.TileLayer.TMS.provider('NaverMap.Cadastral'),
    	'Hybrid Map': L.Proj.TileLayer.TMS.provider('NaverMap.Hybrid'),
    	'Satellite Map': L.Proj.TileLayer.TMS.provider('NaverMap.Satellite').addTo(map),
    };
    */

    //L.control.layers(baseLayers, null, {collapsed: true}).addTo(map);
    map.setView([37.56635278, 126.9779528], 10);
    
    // khj 20160423 : 검색결과용 layer
    dataSearchResultLayer = L.layerGroup().addTo(map);
    
    var selectedAreaStyleOption = {style: {
        weight: 2,
        color: "#0000ff",
        opacity: 1,
        fillColor: "#8888ff",
        fillOpacity: 0.5
		}
	};
    selectedAreaLayer = L.geoJson([], selectedAreaStyleOption).addTo(map);
    
    var candidateAreaStyleOption = {style: {
        weight: 2,
        color: "#ff0000",
        opacity: 1,
        fillColor: "#ff8888",
        fillOpacity: 0.5
		}
	};
    candidateAreaLayer = L.geoJson([], candidateAreaStyleOption).addTo(map);

    L.control.scale().addTo(map);

//    locationFilter = new L.CustomizedLocationFilter();
//    addLocationFilter(locationFilter);
    
}

function removeAlldataSearchResults()
{
	dataSearchResultLayer.clearLayers();
}

function addDataSearchResult(position, title)
{
	searchTitle = title;
	var latlng = L.GeoJSON.coordsToLatLng(position.coordinates, true);
	if(position)
		map.setView(latlng, 18);
	
	var marker = L.marker(latlng);
	dataSearchResultLayer.addLayer(marker);
	if(title)
		marker.bindPopup(title).openPopup();
}

function removeAllSelectedArea()
{
	selectedAreaSum.geometries.length = 0;
	selectedAreaLayer.clearLayers();
}

function addSelectedArea(area)
{
	selectedAreaSum.geometries.push(area);
	selectedAreaLayer.clearLayers();
	selectedAreaLayer.addData(selectedAreaSum);
}

function removeSelectedArea(indexToBeRemoved)
{
	if(indexToBeRemoved < 0 || indexToBeRemoved >= selectedAreaSum.geometries.length)
		return;
	
	selectedAreaSum.geometries.splice(indexToBeRemoved, 1);
	selectedAreaLayer.clearLayers();
	selectedAreaLayer.addData(selectedAreaSum);
}

function isAnySelectedArea()
{
	return selectedAreaSum.geometries.length > 0;
}

function removeCandidateAreaFromLayer()
{
	candidateAreaLayer.clearLayers();
}

function addCandidateAreaToLayer()
{
	if(candidateArea == null)
		return;
	
	candidateAreaLayer.clearLayers();
	candidateAreaLayer.addData(candidateArea);
}

function registerCandidateArea(area)
{
	candidateArea = area;
}

function deleteCandidateArea()
{
	candidateArea = null;
}

function moveCandidateToSelected()
{
	if(candidateArea == null)
		return;
	
	addSelectedArea(candidateArea);
}

function fitToSelectedArea()
{
	map.fitBounds(selectedAreaLayer.getBounds());
}

function fitToCandidateArea()
{
	map.fitBounds(candidateAreaLayer.getBounds());
}

function fitToBothSelectedAndCandidateArea()
{
	var bounds = selectedAreaLayer.getBounds();
	
	bounds.extend(candidateAreaLayer.getBounds().getSouthWest());
	bounds.extend(candidateAreaLayer.getBounds().getNorthEast());
	
	map.fitBounds(bounds);
}

function addLocationFilter()
{
	if(map)
		locationFilter.addTo(map);
}