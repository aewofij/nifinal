var markers = {
  '123.mov': [ { time: 5850, message: 'start' }
             , { time: 13000, message: 'stop' } ]
};

var state = {
  currentVideo: null,
  lastTime: 0,
  enabled: true
};

function video (name) {
  state.currentVideo = name;
}

function time (ms) {
  if (state.currentVideo !== null) {
    if (markers[state.currentVideo] !== undefined) {
      markers[state.currentVideo].forEach(function (marker) {
        if ((state.lastTime < marker.time && ms >= marker.time)
            || (state.lastTime < marker.time && ms < state.lastTime)) {
          outlet(0, marker.message);
        }
      });
    }
  }
  state.lastTime = ms;
}

function enable (isEnabled) {
  enabled = isEnabled ? true : false; // get around duck typing
}
