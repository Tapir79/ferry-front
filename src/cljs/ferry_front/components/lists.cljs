(ns ferry-front.components.lists)

(defn bullet-list [itemkey items]
  [:ul
   (for [item items]
     ^{:key item} [:li (itemkey item)])]
  )


(defn lister [items]
  [:ul
   (for [item items]
     ^{:key item} [:li (:message item)])])


#_(lister tests)
#_(bullet-list :mykey mydata)
