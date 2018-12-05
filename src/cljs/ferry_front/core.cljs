(ns ferry-front.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [stylefy.core :as stylefy]
   [ferry-front.events :as events]
   [ferry-front.views :as views]
   [ferry-front.config :as config]
   [ferry-front.components.loader :refer [compass-loader]]
   [ferry-front.styles.global :refer [init-global-styles]]))


(re-frame/reg-sub   ;; we can check if there is data
  :initialised?          ;; usage (subscribe [:initialised?])
  (fn  [db _]
    (and (not (nil? (:linesegments db)))
         (not (nil? (:stop-routes db))))))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn main-loader []
  [:div {:class "flex justify-center items-center h-screen w-screen"}
   (compass-loader)])

(defn top-panel    ;; this is new
  []
  (let [ready?  (re-frame/subscribe [:initialised?])]
    (if-not @ready?         ;; do we have good data?
      (main-loader)   ;; tell them we are working on it
      [views/main-panel])))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (re-frame/dispatch [::events/initialize-db])
  (println "At core")
  (reagent/render [top-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  #_(re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (stylefy/init {:global-vendor-prefixes {::stylefy/vendors ["webkit" "moz" "o"]
                                          ::stylefy/auto-prefix #{:border-radius}}})
  (init-global-styles)
  (mount-root))
