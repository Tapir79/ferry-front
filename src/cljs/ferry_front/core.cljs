(ns ferry-front.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [stylefy.core :as stylefy]
   [ferry-front.events :as events]
   [ferry-front.config :as config]
   [ferry-front.styles.global :refer [init-global-styles]]
   [ferry-front.components.header :refer [header]]
   [ferry-front.views.navigation :refer [main-navigation init-routes!]]
   [ferry-front.util.ws :as ws]))


(re-frame/reg-sub
  :initialised?
  (fn  [db _]
    (and (not (nil? (:linesegments db)))
         (not (nil? (:stop-routes db))))))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn main-page []
  [:div {:class "flex flex-col m-auto max-w-5xl"}
   [header]
   [main-navigation]
  ])


(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (re-frame/dispatch [::events/initialize-db])
  (init-routes!)
  (ws/start-router!)
  (reagent/render [main-page]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (dev-setup)
  (stylefy/init {:global-vendor-prefixes {::stylefy/vendors ["webkit" "moz" "o"]
                                          ::stylefy/auto-prefix #{:border-radius}}})
  (init-global-styles)
  (mount-root))
