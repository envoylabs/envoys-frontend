[![Build Status](https://travis-ci.org/the-frey/envoys-frontend.svg?branch=master)](https://travis-ci.org/the-frey/envoys-frontend)

# envoys-frontend

A [re-frame](https://github.com/Day8/re-frame) application for the envoys frontend. The backend is served with a Serverless application.

Generated with:

    lein new re-frame envoys-frontend +10x +cider +test +aliases +routes +garden 

## Development Mode

### Start Cider from Emacs:

Put this in your Emacs config file:

```
(setq cider-cljs-lein-repl
	"(do (require 'figwheel-sidecar.repl-api)
         (figwheel-sidecar.repl-api/start-figwheel!)
         (figwheel-sidecar.repl-api/cljs-repl))")
```

Navigate to a clojurescript file and start a figwheel REPL with `cider-jack-in-clojurescript` or (`C-c M-J`)

If you have issues with that and you're on OSX, it could be because you've launched Emacs as a Mac application. Try adding the following to your shell config and lauching it from inside the working directory:

    alias emacs='/Applications/Emacs.app/Contents/MacOS/Emacs'

### Compile css:

Compile css file once.

```
lein garden once
```

Automatically recompile css file on change.

```
lein garden auto
```

CSS also gets automatically built when running `lein build` and recompiled on change with `lein dev`.

### Run application:

```
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

### Run tests:

```
lein clean
lein doo phantom test once
```

The above command assumes that you have [phantomjs](https://www.npmjs.com/package/phantomjs) installed. However, please note that [doo](https://github.com/bensu/doo) can be configured to run cljs.test in many other JS environments (chrome, ie, safari, opera, slimer, node, rhino, or nashorn).

## Production Build


To compile clojurescript to javascript:

```
lein build
```
