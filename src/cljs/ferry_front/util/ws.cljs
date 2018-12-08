(ns ferry-front.util.ws
  (:require-macros
    [cljs.core.async.macros :as asyncm :refer (go go-loop)])
  (:require
    [re-frame.core :as rf]
    [taoensso.encore :as encore :refer (debugf)]
    [cljs.core.async :as async :refer (<! >! put! chan)]
    [taoensso.sente  :as sente :refer (cb-success?)]))

(enable-console-print!)

(defn get-backend-url []
  "ws://127.0.0.1:8080/booking-status")

(let [{:keys [chsk ch-recv send-fn state]}
      (sente/make-channel-socket-client! "/booking-status"
                                         {:type :auto
                                          :chsk-url-fn get-backend-url
                                          })]
  (def chsk       chsk)
  (def ch-chsk    ch-recv)
  (def chsk-send! send-fn)
  (def chsk-state state)
  )

(defmulti event-msg-handler :id)

(defmethod event-msg-handler :default
  [{:as ev-msg :keys [event]}]
  (println "Unandled event: " event))

(defmethod event-msg-handler :chsk/state
  [{:as ev-msg :keys [?data]}]
  (println "event" ?data))


(defmethod event-msg-handler :chsk/handshake
  [{:as ev-msg :keys [?data]}]
  ;; ???
  )

(defmethod event-msg-handler :chsk/recv
  [{:as ev-msg :keys [?data]}]
  (println "received message: " ?data)
  (rf/dispatch [:ws-booking-status-received ?data]))

(defn event-msg-handler* [{:as ev-msg :keys [id ?data event]}]
  (event-msg-handler ev-msg))

(def router (atom nil))

(defn stop-router! [] (when-let [stop-f @router] (stop-f)))

(defn start-router! []
  (stop-router!)
  (reset! router (sente/start-client-chsk-router! ch-chsk event-msg-handler*)))
