(ns web.core
  [:require [org.httpkit.server :as s] ;; namespace the server
            [compojure.core :refer [routes POST GET ANY]]]) ;; refer specific functions

;; private singleton that cannot be accessed from outside namespace of this file.
(defonce ^:private server (atom nil))

;; Request middleware/interceptor
;; allows when mistake such as when user type extra / at the end e.g. http://127.0.0.1:8080/someName/
;; and returns response of http://127.0.0.1:8080/someName
(defn remove-trailing-slashes [handler]
  (fn [req]
    (let [uri (:uri req)
          not-root? (not= uri "/")
          ends-with-slash? (.endsWith ^String uri "/")
          fixed-uri (if (and not-root?
                             ends-with-slash?)
                      (subs uri 0 (dec (count uri)))
                      uri)
          fixed-req (assoc req :uri  fixed-uri)]
      (handler fixed-req))))


;; app is a request handler
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

;; create server
(defn create-server []
  (s/run-server (remove-trailing-slashes (app)) {:port 8080}))

;; run server
(defn run-server []
  (reset! server (create-server)))

;; stop server
(defn stop-server [server]
  (server :timeout 100))

