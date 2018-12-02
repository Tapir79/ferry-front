(ns ferry-front.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [stylefy.core :as stylefy]
   [ferry-front.events :as events]
   [ferry-front.views :as views]
   [ferry-front.config :as config]
   [ferry-front.styles.global :refer [init-global-styles]]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  #_(re-frame/dispatch [::events/init-tests])
  (re-frame/dispatch [::events/initialize-db])
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  #_(re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (stylefy/init {:global-vendor-prefixes {::stylefy/vendors ["webkit" "moz" "o"]
                                          ::stylefy/auto-prefix #{:border-radius}}})
  (init-global-styles)
  (mount-root))
