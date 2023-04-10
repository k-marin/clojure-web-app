# web

A Clojure library designed to ... well, that part is up to you.

## Example from tutorial 

https://www.youtube.com/watch?v=LcpbBth7FaQ

### Load in REPL

(require '[web.core :as c])

(def server (c/create-server))

Check http://127.0.0.1:8080 on your browser that it is running then run 

(c/stop-server server)

Confirm that http://127.0.0.1:8080 stopped running.

## Install lein
    
    $ brew install lein

    $ lein new app web



## Create jar file
   
    $ lein uberjar


## Run the web app

    $ java -jar web-0.1.0-SNAPSHOT-standalone.jar [args]

## License

Copyright © 2023 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
