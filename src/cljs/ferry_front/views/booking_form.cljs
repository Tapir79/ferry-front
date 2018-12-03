(ns ferry-front.views.booking-form
  (:require [re-frame.core :as re-frame]
            [ferry-front.subs :as subs]
            [stylefy.core :as stylefy]
            [ferry-front.events.events-timetables :as et-events]
            [ferry-front.styles.global :refer [mobile-width]]))

(enable-console-print!)

(defn handle-search-button-click []
  ())

(def booking-form-style {:width "97.5%"})

(def input-icon-style {:top "1.8em" :left "0.5em" :pointer-events "none"
                 ::stylefy/media {{:max-width mobile-width} {:top "0.7em"}}})

(defn styled-select [id label placeholder options on-change-function icon]
  [:div {:class "flex-grow flex-shrink mb-1 sm:m-1 w-full sm:w-auto relative"}
   [:label {:for id :class "hidden sm:block text-sm font-bold sm:pr-1 text-grey-darker"} label]
   [:select {:id id :class "rounded py-2 pl-8 px-1 bg-white w-full cursor-pointer" :on-change on-change-function }
    [:option {:class "text-grey-darker"} placeholder]
    (options)]
   [:i (stylefy/use-style input-icon-style {:class (str icon " absolute z-10 text-grey-darker")})]])

(defn search-button []
  [:div {:class "flex-grow flex-shrink w-full sm:w-auto my-1 sm:m-1"}
   [:button {:class "bg-blue hover:bg-blue-dark font-semibold text-white p-2 rounded w-full"
             :type "button"
             :on-click handle-search-button-click
             } "Search"]])

(def exchange-icon-style {:top "2.1em" :right "-1.1em"
                          ::stylefy/media {{:max-width mobile-width}
                                           {:top "0.7em" :right "-1.3em" :transform "rotate(90deg)"}}})


(defn exchange-harbors-wrapper [wrapped-input]
  [:div {:class "flex-grow flex-shrink w-full sm:w-auto relative mr-6 sm:mr-5"}
   (wrapped-input)
   [:i (stylefy/use-style
         exchange-icon-style
         {:class "fas fa-exchange-alt absolute z-10 text-grey-darker hover:text-black cursor-pointer"})]])

(def datepicker-style {:padding-top "0.375rem" :padding-bottom "0.375rem"})

(defn datepicker [id label on-change-function]
  [:div {:class "flex-grow flex-shrink mb-1 sm:m-1 w-full sm:w-auto relative"}
   [:label {:for id :class "hidden sm:block text-sm font-bold sm:pr-1 text-grey-darker"} label]
   [:input (stylefy/use-style datepicker-style {:class "rounded py-1 pl-8 bg-white w-full"
            :type "date"
            :on-change on-change-function})]
   [:i (stylefy/use-style input-icon-style {:class "far fa-calendar-alt absolute z-10 text-grey-darker"})]])

(defn get-lines-options [lines]
  (fn []
    (for [line @lines]
      [:option {:key (:id line) :value (:id line)} (:name_fi line) ])))

(defn get-stops-options [stops]
  (fn []
    (for [stop @stops]
      [:option {:key (:id stop) :value (:id stop)} (:name stop) ])))

(defn form [lines stops]
  [:div (stylefy/use-style booking-form-style {:class "sm:w-full -ml-1 lg:ml-3 sm:mx-1 pb-1"})
   [:form {:class "w-full flex flex-wrap items-center sm:items-end bg-blue-lighter rounded p-1 pb-0 sm:p-2 ml-2 mb-1"}
    (styled-select "route-selection" "Route" "Select route" (get-lines-options lines) #(re-frame/dispatch [::et-events/change-line (.-value (.-target %))] ) "fas fa-route")
    (exchange-harbors-wrapper
      (fn [] (styled-select "from" "from" "Departure harbor" (get-stops-options stops) #(println (.-value (.-target %))) "fas fa-anchor")))
    (styled-select "to" "to" "Arrival harbor" (get-stops-options stops) #(println (.-value (.-target %))) "fas fa-anchor")
    (datepicker "departure" "departure" #(println (.value (.-target %))))
    (search-button)]])

(defn booking-form []
  (let [lines (re-frame/subscribe [::subs/lines])
        stops (re-frame/subscribe [::subs/stops])]
  (when-not (or (empty? @lines) (empty? @stops)))
    (form lines stops)))
