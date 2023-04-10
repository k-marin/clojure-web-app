(ns web.core
  [:require [org.httpkit.server :as s] ;; namespace the server
            [compojure.core :refer [routes POST GET ANY]]]) ;; refer specific functions

(defn handler [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "<h1> HI!!!! </h1>"})

(defn app []
  (routes
   (GET "/" [:as req]
     {:status 200
      :headers {"Content-Type" "text/html"}
      :body "<h1> HI from root!!! </h1>"})
   (GET "/:user-name" [user-name :as req]
     {:status 200
      :headers {"Content-Type" "text/html"}
      :body (format "<h1> HI from  %s </h1>" user-name)})))

(defn create-server []
  (s/run-server (app) {:port 8080}))

(defn stop-server [server]
  (server :timeout 100))


