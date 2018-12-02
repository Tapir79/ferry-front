(ns ferry-front.views.booking-form
  (:require [re-frame.core :as re-frame]
    [reagent.core :as reagent :refer [atom]]
    [ferry-front.events :as events]
    [ferry-front.subs :as subs]
    [ferry-front.components.inputs :as inputs]
    [ferry-front.components.buttons :as buttons]
    [ferry-front.components.lists :as lists]))

(defn own-select [id label placeholder options on-change-function]
  [:div {:class "mb-2" }
   [:label {:for id :class "text-sm font-bold inline-block text-left w-1/5"} label]
   [:select {:id id :class "rounded py-2 ph-3 bg-white w-3/5" :on-change on-change-function}
    [:option {:class "text-grey-darker"} placeholder ]
    (for [option options]
      ^{:key option} [:option option] )]])

(defn booking-form []
  [:div {:class "w-full"}
    [:form {:class "xs:flex bg-blue rounded px-8 pt-6 pb-8 mb-4"}
     ; dispatch :chosen-line-geom (arvot :sodra :norra :tvar)
      (own-select "route-selection" "Route" "Select route" (list "södra linje" "norra linje" "tvarlinje") #(println "route"))
     ; dispatch from ja to kombinaatiolla tilaan oikea segmentti
      (own-select "from" "from" "Departure harbor" (list "satama1" "satama2") #(println "harbor"))
      (own-select "to" "to" "Arrival harbor" (list "satama1" "satama2") #(println "arrival"))]])
