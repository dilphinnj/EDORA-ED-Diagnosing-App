;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; ED Algorithm Design
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Import some commonly-used classes
(import javax.swing.*)
(import java.awt.*)
(import java.awt.event.*)

;; Don't clear defglobals on (reset)
(set-reset-globals FALSE)

(defglobal ?*crlf* = "")
(defglobal ?*risk_Score* = 0)
(defglobal ?*eat_time* = 0)
(defglobal ?*analysis* = "RISK DIAGNOSIS
=================>>		")
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Question and answer templates

(deftemplate question
  (slot text)
  (slot type)
  (multislot valid)
  (slot id))

(deftemplate answer
  (slot id)
  (slot text))

(do-backward-chaining answer)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Module trigger

(defmodule trigger)

(defrule trigger::supply-answers
  (declare (auto-focus TRUE))
  (MAIN::need-answer (id ?id))
  (not (MAIN::answer (id ?id)))
  (not (MAIN::ask ?))
  =>
  (assert (MAIN::ask ?id))
  (return))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; ED diagnosing rules

(defrule ED-risk-status-Bulimia-symptoms
 (declare (auto-focus TRUE))
 (vomit-yes)(binge-yes)(test-complete)(some-signs)   
 (test (> (integer ?*eat_time*) 72))
  =>
 (printout t  " ED-risk-status-Bulimia-symptoms")  
 (bind ?*analysis* (call ?*analysis* concat "
        => Patient is showing signs of having Bulimia"))
 (recommend-action ?*analysis* ))


(defrule ED-risk-status-Aneroxia-symptoms
  (declare (auto-focus TRUE))
  (aware-calories-yes) (avoide-eating-yes)(test-complete)(some-signs)
    (test (< (integer ?*eat_time*) 52))
  =>
  (bind ?*analysis* (call ?*analysis* concat "
        => Patient is showing signs of having Aneroxia"))
  (printout t "ED-risk-status-Aneroxia-symptoms" crlf)
  (recommend-action ?*analysis* ))


(defrule ED-risk-status-No-Signs
  (declare (auto-focus TRUE))
  (test-complete)
 (test (< (integer ?*risk_Score*) 11))
  =>
 (bind ?*analysis* (call ?*analysis* concat "
 Patient is not showing any signs of having any Eating Disorder!"))
 (printout t "R2=>"?*risk_Score* "ED-risk-status-No-Signs" crlf)
 (recommend-action ?*analysis* )) 

    
(defrule ED-risk-status-Some-Signs
  (declare (auto-focus TRUE))
  (test-complete)
  (test (>= (integer ?*risk_Score*) 11))
 =>
 (bind ?*analysis* (call ?*analysis* concat "
 Patient is in risk of having an Eating Disorder... Please check with consultant for further treatment!")) 
 (assert (some-signs))
 (printout t "R2=>"?*risk_Score* "|ED-risk-status-Some-Signs"   crlf)
 (recommend-action ?*analysis* ))
  
    
(defrule eat-rich-food-no
  (declare (auto-focus TRUE))
  (answer (id 22) (text ?t))
  (test (> (integer ?t) 25)) 
    =>
  (assert (test-complete)))

(defrule eat-rich-food
  (declare (auto-focus TRUE))
  (answer (id 22) (text ?t))
  (test (<= (integer ?t) 25))    
  =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1))
  (printout t "You avoide eating many of the rich food!" crlf)
  (printout t "RISK after rf =>"?*risk_Score* crlf)
  (assert (test-complete)))

(defrule eat-diet-food
  (declare (auto-focus TRUE))
  (answer (id 21) (text yes))
  
  =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1)))

(defrule food-control-life
  (declare (auto-focus TRUE))
  (answer (id 20) (text yes))
  
  =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1)))

(defrule avoide-sweets
  (declare (auto-focus TRUE))
  (answer (id 19) (text ?t))
  (test (<= (integer ?t) 25))    
  =>
 (bind ?*risk_Score* (+ ?*risk_Score* 1))
 (printout t "You avoide eating many of the food with high sugar content" crlf))

(defrule check-time-value
  (declare (auto-focus TRUE))
  (breakfast-yes) (lunch-yes) (dinner-yes) 
  (test (or (>= (integer ?*eat_time*) 61) (<=(integer ?*eat_time*) 41)))
    
  =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1))
  (assert (full-time-complete))
  (printout t "complete time ::" ?*eat_time* crlf))

(defrule dinner-time-value
  (declare (auto-focus TRUE))
  (answer (id 18) (text ?t)) 
  =>
  (bind ?*eat_time* (+ ?*eat_time* ?t))
  (assert (dinner-yes))   
  (printout t "dinner time ::" ?t crlf))

(defrule lunch-time-value
  (declare (auto-focus TRUE))
  (answer (id 17) (text ?t))     
  =>
  (bind ?*eat_time* (+ ?*eat_time* ?t))
  (assert (lunch-yes))
  (printout t "lunch time ::" ?t crlf))

(defrule breakfast-time-value
  (declare (auto-focus TRUE))
  (answer (id 16) (text ?t))     
  =>
  (bind ?*eat_time* (+ ?*eat_time* ?t))
  (assert (breakfast-yes))
  (printout t "breakf time ::" ?t crlf))

(defrule others-think-thin
  (declare (auto-focus TRUE))
  (answer (id 15) (text yes))
  
  =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1)))

(defrule desire-to-be-thinner
  (declare (auto-focus TRUE))
  (answer (id 14) (text yes))
  
  =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1)))

(defrule feel-guilty-after-eat
  (declare (auto-focus TRUE))
  (answer (id 13) (text yes))
  
  =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1)))

(defrule vomit-after-eat
  (declare (auto-focus TRUE))
  (answer (id 12) (text yes))      
   =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1))
  (assert (vomit-yes)))

(defrule high-carbo-food-value
  (declare (auto-focus TRUE))
  (answer (id 11) (text ?t))    
  (test (<= (integer ?t) 25))    
  =>
 (bind ?*risk_Score* (+ ?*risk_Score* 1))
 (printout t "You avoide eating many of the fo0d with high carbohydrate" crlf))

(defrule calorie-content-aware
  (declare (auto-focus TRUE))
  (answer (id 10) (text yes))      
   =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1))
  (assert (aware-calories-yes)))

(defrule cut-food-small-pieces
  (declare (auto-focus TRUE))
  (answer (id 9) (text yes))      
   =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1)))

(defrule eat-binges
  (declare (auto-focus TRUE))
  (answer (id 8) (text yes))      
   =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1))
  (assert (binge-yes)))

(defrule preoccupied-food
  (declare (auto-focus TRUE))
  (answer (id 7) (text yes))      
   =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1)))

(defrule avoide-eating
  (declare (auto-focus TRUE))
  (answer (id 6) (text yes))      
   =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1))
  (assert (avoide-eating-yes)))

(defrule overweight-terrified
  (declare (auto-focus TRUE))
  (answer (id 5) (text yes))      
   =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1)))

(defrule request-life-style-status
  (declare (auto-focus TRUE))
  (answer (id 1) (text no))
  =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1)))

(defrule request-schooling-status
  (declare (auto-focus TRUE))
  (answer (id 1) (text yes))
  (answer (id 2) (text yes))
  =>
  (bind ?*risk_Score* (+ ?*risk_Score* 1))
  (assert (answer (id 3) (text no)))
  (assert (answer (id 4) (text no))))


 
;; (halt)) 

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Results output

(deffunction recommend-action (?action)
  "Give final instructions to the user"
  (call JOptionPane showMessageDialog ?*frame*
        (str-cat ?action)
        "Recommendation"
        (get-member JOptionPane INFORMATION_MESSAGE)))
  
(defadvice before halt (?*qfield* setText "Close window to exit"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Module ask

(defmodule ask)

(deffunction ask-user (?question ?type ?valid)
  "Set up the GUI to ask a question"
  (?*qfield* setText ?question)
  (?*apanel* removeAll)
  (if (eq ?type multi) then
    (?*apanel* add ?*acombo*)
    (?*apanel* add ?*acombo-ok*)
    (?*acombo* removeAllItems)
    (foreach ?item ?valid
             (?*acombo* addItem ?item))
    else
    (?*apanel* add ?*afield*)
    (?*apanel* add ?*afield-ok*)
    (?*afield* setText ""))
  (?*apanel* validate)
  (?*apanel* repaint))

(deffunction is-of-type (?answer ?type ?valid)
  "Check that the answer has the right form"
  (if (eq ?type multi) then
    (foreach ?item ?valid
             (if (eq (sym-cat ?answer) (sym-cat ?item)) then
               (return TRUE)))
    (return FALSE))
    
  (if (eq ?type number) then
    (return (is-a-number ?answer)))
    
  ;; plain text
  (return (> (str-length ?answer) 0)))

(deffunction is-a-number (?value)
  (try
   (integer ?value)
   (return TRUE)
   catch 
   (return FALSE)))

(defrule ask::ask-question-by-id
  "Given the idifier of a question, ask it"
  (declare (auto-focus TRUE))
  (MAIN::question (id ?id) (text ?text) (valid $?valid) (type ?type))
  (not (MAIN::answer (id ?id)))
  (MAIN::ask ?id)
  =>
  (ask-user ?text ?type ?valid)
  ((engine) waitForActivations))

(defrule ask::collect-user-input
  "Check an answer returned from the GUI, and optionally return it"
  (declare (auto-focus TRUE))
  (MAIN::question (id ?id) (text ?text) (type ?type) (valid $?valid))
  (not (MAIN::answer (id ?id)))
  ?user <- (user-input ?input)
  ?ask <- (MAIN::ask ?id)
  =>
  (if (is-of-type ?input ?type ?valid) then
    (retract ?ask ?user)
    (assert (MAIN::answer (id ?id) (text ?input)))
    (return)
    else
    (retract ?ask ?user)
    (assert (MAIN::ask ?id))))

;; Main window
(defglobal ?*frame* = (new JFrame "ED RISK TEST"))
(?*frame* setDefaultCloseOperation (get-member JFrame HIDE_ON_CLOSE))
(?*frame* setSize 600 400)
(?*frame* setVisible TRUE)

;; Question field
(defglobal ?*qfield* = (new JTextArea 10 40))
(bind ?scroll (new JScrollPane ?*qfield*))
((?*frame* getContentPane) add ?scroll)
(?*qfield* setText "Please wait...")

;; Answer area
(defglobal ?*apanel* = (new JPanel))
(defglobal ?*afield* = (new JTextField 20))
(defglobal ?*afield-ok* = (new JButton OK))

(defglobal ?*acombo* = (new JComboBox (create$ "yes" "no")))
(defglobal ?*acombo-ok* = (new JButton <==Select-Answer))


(?*apanel* add ?*afield*)
(?*apanel* add ?*afield-ok*)
((?*frame* getContentPane) add ?*apanel* (get-member BorderLayout SOUTH))
(?*frame* validate)
(?*frame* repaint)

(deffunction read-input (?EVENT)
  "An event handler for the user input field"
  (assert (ask::user-input (sym-cat (?*afield* getText)))))

(bind ?handler (new jess.awt.ActionListener read-input (engine)))
(?*afield* addActionListener ?handler)
(?*afield-ok* addActionListener ?handler)

(deffunction combo-input (?EVENT)
  "An event handler for the combo box"
  (assert (ask::user-input (sym-cat (?*acombo* getSelectedItem)))))
(bind ?handler (new jess.awt.ActionListener combo-input (engine)))
(?*acombo-ok* addActionListener ?handler)

;;(deffunction chkbox-input (?EVENT)
;;  "An event handler for the check box"
;;  (assert (ask::user-input (sym-cat (?*achkbox* getSelectedItem)))))
;;(bind ?handler (new jess.awt.ActionListener chkbox-input (engine)))
;;(?*achkbox-ok* addActionListener ?handler)



(deffacts question-data
  "The questions the system can ask."
  (question (id 1) (type multi) (valid yes no)(text "
        Are you still schooling?")) 
  (question (id 2) (type multi) (valid yes no)(text "
        Do you attend an international school?")) 
  (question (id 3) (type multi) (valid yes no)(text "
        Are you working / or in University?"))
  (question (id 4) (type multi) (valid yes no)(text "
        Do you believe your life style is lavish?"))
  (question (id 5) (type multi) (valid yes no)(text "
        Are you terrified of being over-weight?")) 
  (question (id 6) (type multi) (valid yes no)(text "
        Do you avoid eating when you are hungry?"))   
  (question (id 7) (type multi) (valid yes no)(text "
        Do you find your self pre-occupied with food?"))  
  (question (id 8) (type multi) (valid yes no)(text "
        Have you gone on eating binges where you felt that you may not be able to stop?"))  
  (question (id 9) (type multi) (valid yes no)(text "
        Do you cut food into small pieces?"))
  (question (id 10) (type multi) (valid yes no)(text "
        Are you aware of the calorie content of the food that you eat?"))  
  (question (id 11) (type number)(valid)(text "
        Do you enjoy eating the following food? -> 
        Please Enter a number as a percentage of the food you would consume from the list (0 - 100) =>  
                
•	Bread
•	Biscuits
•	Malu Paan
•	Cereal
•	Short Eats
•	Pan cakes
•	Corn Flakes
•	Buns
•	Sandwiches
•	Short Eats
•	Pizza
•	Potatoes
•	French Fries
•	Roti / Chappathi / Naan / Other types of roti
•	String hoppers 
•	Bread 
•	Kottu
•	Pasta
•	Pizza
•	Pittu
•	Rotti / Egg roti /Chappathi / Godamba rotti / Naan / Other types of roti
•	Pancakes
•	Dose
•	Bread
•	Hoppers
•	Parata ") )
  (question (id 12) (type multi) (valid yes no)(text "
           Do you vomit after you eat?"))
  (question (id 13) (type multi) (valid yes no)(text "
           Do you feel extremely guilty after you eat?"))  
  (question (id 14) (type multi) (valid yes no)(text "
           Are you preoccupied with a desire to be thinner?")) 
  (question (id 15) (type multi) (valid yes no)(text "
           Does Other people think that you are too thin?")) 
  (question (id 16) (type number)(valid)(text "
           How much of time do you take to eat your breakfast?")) 
  (question (id 17) (type number)(valid)(text "
           How much of time do you take to eat your lunch?"))
  (question (id 18) (type number)(valid)(text "
           How much of time do you take to eat your dinner?")) 
  (question (id 19) (type number)(valid)(text "
           Do you enjoy eating the following food? -> 
           Please Enter a number as a percentage of the food you would consume from the list (0 - 100)
            
•	Chocolates 
•	Ice Creames
•	Corn Flakes
•	Desserts (Puddings, Mousses, etc...)
•	Milk Shakes
•	Yogurts
•	Fruits / Fruit Juices
•	Pan Cakes
•	Cakes / Icing
•	Candy
•	Sweets
•	Marshmellows
•	Nuttella "))      
    
    (question (id 20) (type multi) (valid yes no)(text "
           Do you feel that food control your life and displays self-control around food?"))     
    (question (id 21) (type multi) (valid yes no)(text "
           Do you eat diet food and engage in dieting behavior?")) 
    (question (id 22) (type number)(valid)(text "
           Do you enjoy eating the following food? -> 
           Please Enter a number as a percentage of the food you would consume from the list (0 - 100)
            
  	•	Cheese
	•	Butter 
	•	Pan cakes
	•	Eggs
	•	Meat (Chicken / Pork / Beef / Eggs / Goat / or any other type of meat)
	•	Pizza
	•	Noodles
	•	Burgers
	•	French Fries
	•	Pasta
	•	Pizza
	•	Spaghetti 
	•	Chocolate Mousse
	•	Pudding
	•	Cakes
	•	Ice cream
	•	Candy
	•	Nutella 
	•	Sweets  
            
                       Please click X to close test!       ")) 
  (MAIN::ask 1))

  
(reset)
(run)
(printout t "R=>"?*risk_Score* )
(printout t "E=>"?*eat_time* )
