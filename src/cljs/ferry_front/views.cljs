(ns ferry-front.views
  (:require
    [re-frame.core :as re-frame]
    [ferry-front.subs :as subs]
    [ferry-front.views.booking :refer [booking-main]]
    [ferry-front.leaflet.core :refer [leaflet]]
    [re-frame.core :as re-frame]
    [ferry-front.subs :as subs]
    [ferry-front.events.events-timetables :as events-timetables]
    [ferry-front.components.booking-form :as booking-form]
    [ferry-front.leaflet.core :refer [leaflet]]
    [clojure.string :as str]
    [ferry-front.views.map :refer [main-map]]
    [ferry-front.views.analysis :as analysis]))


(defn main-panel []

    [:div
     [booking-main]

     [main-map]
     [:div {:class "flex"}]
     #_[analysis/analysis-main]])

(defn test-view []
  [:div {:class "bg-red"}
   ]
  )

