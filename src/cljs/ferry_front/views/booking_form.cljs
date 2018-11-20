(ns ferry-front.views.booking-form
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom]]
            [ferry-front.events :as events]
            [ferry-front.subs :as subs]
            [ferry-front.components.inputs :as inputs]
            [ferry-front.components.buttons :as buttons]))

(enable-console-print!)

(def linje {:1 "Norra linjen"
            :2 "Tvärgående linjen"
            :3 "Södra linjen"})

(def norralinjen-from {:1 "Asterholma"
                       :2 "Enklinge"
                       :3 "Hummelvik"
                       :4 "Jurmo"
                       :5 "Kumlinge"
                       :6 "Lappo"
                       :7 "Osnäs"
                       :8 "Torsholma"
                       :9 "Åva"})

(def mv {:username "jimmy"
         :pets     [{:name "Rex"
                     :type :dog}
                    {:name "Sniffles"
                     :type :hamster}]})

(def norralinjen {:norra [{:line-from "Asterholma"
                           :lines-to  ["Lappo"
                                        "Torsholma"]}
                          {:line-from "Enklinge"
                           :lines-to  [{:3 {:line "Hummelvik"}
                                        :5 {:line "Kumlinge"}
                                        :6 {:line "Lappo"}
                                        :8 {:line "Torsholma"}}]}]})


#_#_#_#_#_#_#_{:line "Hummelvik"
               :to   {:2 {:line "Enklinge"}
                      :5 {:line "Kumlinge"}
                      :6 {:line "Lappo"}
                      :8 {:line "Torsholma"}}}
    {:line "Jurmo"
     :to   {:9 {:line "Åva"}}}
    {:line "Kumlinge"
     :to   {:2 {:line "Enklinge"}
            :3 {:line "Hummelvik"}
            :6 {:line "Lappo"}
            :8 {:line "Torsholma"}}}
    {:line "Lappo"
     :to   {:1 {:line "Asterholma"}
            :2 {:line "Enklinge"}
            :3 {:line "Hummelvik"}
            :5 {:line "Kumlinge"}
            :8 {:line "Torsholma"}}}
    {:line "Osnäs"
     :to   {:9 {:line "Åva"}}}
    {:line "Torsholma"
     :to   {:1 {:line "Asterholma"}
            :2 {:line "Enklinge"}
            :3 {:line "Hummelvik"}
            :5 {:line "Kumlinge"}
            :6 {:line "Lappo"}}}
    {:line "Åva"
     :to   {:4 {:line "Jurmo"}
            :7 {:line "Osnäs"}}}

(defn line-loop-from [list]
  ; set line-from db
  (map (fn [[k v]] [:option {:value v} v]) list))

#_(defn line-loop-to [list]
    (map (fn [[k v]] [:option {:value (:line v)} (:line v)]) list))


; vaihda LI elementeiksi
(defn norra-linjen-from []
  [:select {:name "from" :on-change #(re-frame/dispatch [::events/add-to-new-booking [[:line-from] (.-value (.-target %))]])}
   [:option {:value ""} "Välja från"]
   (line-loop-from norralinjen-from)]
  )

#_(defn norra-linjen-to []
    [:select {:name "till" :on-change #(re-frame/dispatch [::events/add-to-new-booking [[:line-to] (.-value (.-target %))]])}
     (let [line-to @(re-frame/subscribe [::subs/:line-to])]
       (when line-to (line-loop-to line-to)))
     ])


(defn booking-form []
  [:form

   [:h1 "Linje och rutt"]
   [:h2 "Välj linje du vill resa på"]
   [:h3 "Dropdown example"]

   [:select {:name "Linje" :on-change #(re-frame/dispatch [::events/change-line-booking (.-value (.-target %))])}
    [:option {:value ""} "välja linjen"]
    [:option {:value (:1 linje)} (:1 linje)]
    [:option {:value (:2 linje)} (:2 linje)]
    [:option {:value (:3 linje)} (:3 linje)]]

   [:br]

   [:h2 "Välj rutt från"]
   #_[:div (when line
             (cond
               (== line (:1 linje)) (norra-linjen-from)
               (== line (:2 linje)) (:2 linje)
               (== line (:3 linje)) (:3 linje)
               :else "No line"))]


   [:h2 "DINA UPPGIFTER"]
   [:br]
   [:h3 "Förnamn"]
   [:br]
   [:input {:name "from" :on-change #(re-frame/dispatch [::events/add-to-new-booking [[:firstname] (.-value (.-target %))]])}]
   [:br]
   [:h3 "Efternamn"]
   [:br]
   [:input {:name "from" :on-change #(re-frame/dispatch [::events/add-to-new-booking [[:lastname] (.-value (.-target %))]])}]
   [:br]
   [:h3 "Address"]
   [:br]
   [:input {:name "from" :on-change #(re-frame/dispatch [::events/add-to-new-booking [[:address] (.-value (.-target %))]])}]
   [:br]
   [:h3 "Postnummer"]
   [:br]
   [:input {:name "from" :on-change #(re-frame/dispatch [::events/add-to-new-booking [[:postnumber] (.-value (.-target %))]])}]
   [:br]
   [:h3 "Ort"]
   [:br]
   [:input {:name "from" :on-change #(re-frame/dispatch [::events/add-to-new-booking [[:land] (.-value (.-target %))]])}]
   [:br]
   [:h3 "LandTelefonnumer"]
   [:br]
   [:input {:name "from" :on-change #(re-frame/dispatch [::events/add-to-new-booking [[:telephone] (.-value (.-target %))]])}]
   [:br]
   [:h3 "E-post"]
   [:br]
   [:input {:name "from" :on-change #(re-frame/dispatch [::events/add-to-new-booking [[:email] (.-value (.-target %))]])}]

   [:br]
   [:h3 "Jag har läst och godkänner Boknings-, ändrings- och avbokningsreglerna."]
   [:br]
   [:p "additional info"]
   [:input {:name "from" :on-change #(re-frame/dispatch [::events/add-to-new-booking [[:info] (.-value (.-target %))]])}]

   [:br]
   [:button {:on-click #(re-frame/dispatch [::events/send-new-booking])} "Boka"]
   ])


