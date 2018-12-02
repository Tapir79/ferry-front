(ns ferry-front.views.booking-form
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom]]
            [ferry-front.events :as events]
            [ferry-front.subs :as subs]
            [stylefy.core :refer [use-style]]))

(defn handle-search-button-click []
  ())

(def booking-form-style {:width "97.5%"})

(defn styled-select [id label placeholder options]
  [:div {:class "flex-grow flex-shrink mb-1 sm:m-1 w-full sm:w-auto"}
   [:label {:for id :class "hidden sm:block text-sm font-bold sm:pr-1 text-grey-darker"} label]
   [:select {:id id :class "rounded py-2 px-1 bg-white w-full"}
    [:option {:class "text-grey-darker"} placeholder ]
    (for [option options]
      ^{:key option} [:option option] )]])

(defn search-button []
  [:div {:class "flex-grow flex-shrink w-full sm:w-auto my-1 sm:m-1"}
   [:button {:class "bg-blue hover:bg-blue-dark font-semibold text-white p-2 rounded w-full sm:w-4/5"
             :type "button"
             :on-click handle-search-button-click
             } "Search"]])

(defn booking-form []
  [:div (use-style booking-form-style {:class "sm:w-full -ml-1 lg:ml-3 sm:mx-1 pb-1"})
    [:form {:class "w-full flex flex-wrap items-center sm:items-end bg-blue-lighter rounded p-1 pb-0 sm:p-2 ml-2 mb-1"}
      (styled-select "route-selection" "Route" "Select route" (list "eka" "toka"))
      (styled-select "from" "from" "Departure harbor" (list "satama1" "satama2"))
      (styled-select "to" "to" "Arrival harbor" (list "satama1" "satama2"))
     (search-button)]])
