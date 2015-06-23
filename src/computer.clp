   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Graphical version of PC Diagnostic Assistant from part 4 of 
;; "Jess in Action"
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Import some commonly-used classes
(import javax.swing.*)
(import java.awt.*)
(import java.awt.event.*)

;; Don't clear defglobals on (reset)
(set-reset-globals FALSE)

(defglobal ?*crlf* = "
")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Question and answer templates

(deftemplate question
  (slot text)
  (slot type)
  (multislot valid)
  (slot ident))

(deftemplate answer
  (slot ident)
  (slot text))

(do-backward-chaining answer)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Module trigger

(defmodule trigger)

(defrule trigger::supply-answers
  (declare (auto-focus TRUE))
  (MAIN::need-answer (ident ?id))
  (not (MAIN::answer (ident ?id)))
  (not (MAIN::ask ?))
  =>
  (assert (MAIN::ask ?id))
  (return))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; power rules

(defrule MAIN::not-plugged-in
  (declare (auto-focus TRUE))
  (answer (ident sound) (text no))
  (answer (ident plugged-in) (text no))
  =>
  (recommend-action "plug in the computer")
  (halt))

(defrule MAIN::power-supply-broken
  (declare (auto-focus TRUE))
  (answer (ident sound) (text no))
  (answer (ident plugged-in) (text yes))
  =>
  (recommend-action "repair or replace power supply")
  (halt))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; sound rules

(defrule MAIN::check-ram
  (declare (auto-focus TRUE))
  (answer (ident sound) (text yes))
  (answer (ident seek) (text no))
  (answer (ident does-beep) (text yes))
  (answer (ident how-many-beeps) (text ?t))
  (test (< (integer ?t) 3))
  =>
  (assert (check loose-ram))
  (recommend-action "check for loose RAM, then continue"))

(defrule MAIN::unknown-sound
  (declare (auto-focus TRUE))
  (answer (ident sound) (text yes))
  (answer (ident seek) (text no))
  (answer (ident does-beep) (text no))
  =>
  (recommend-action "consult a human expert")
  (halt))

(defrule MAIN::motherboard-or-keyboard
  (declare (auto-focus TRUE))
  (answer (ident sound) (text yes))
  (answer (ident seek) (text no))
  (answer (ident does-beep) (text yes))
  (answer (ident how-many-beeps) (text ?t))
  (test (>= (integer ?t) 3))
  =>
  (recommend-action "check keyboard and motherboard")
  (halt))

(defrule MAIN::no-boot-start
  (declare (auto-focus TRUE))
  (answer (ident sound) (text yes))
  (answer (ident seek) (text yes))
  (answer (ident boot-begins) (text no))
  =>
  (recommend-action "check keyboard, RAM, motherboard, and power supply")
  (halt))

(defrule MAIN::boot-start
  (declare (auto-focus TRUE))
  (answer (ident sound) (text yes))
  (answer (ident seek) (text yes))
  (answer (ident boot-begins) (text yes))
  =>
  (recommend-action "consult a software expert")
  (halt))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; RAM rules

(defrule MAIN::loose-ram
  (declare (auto-focus TRUE))
  (check loose-ram)
  (answer (ident loose-ram) (text yes))
  =>
  (recommend-action "remove and reseat memory modules")
  (halt))

(defrule MAIN::faulty-ram
  (declare (auto-focus TRUE))
  (check loose-ram)
  (answer (ident loose-ram) (text no))
  =>
  (recommend-action "replace memory modules one by one and retest")
  (halt))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; domain rules

(defrule MAIN::right-architecture
  (declare (auto-focus TRUE))
  (explicit (answer (ident hardware) (text ~x86)))
  =>
  (recommend-action "consult a human expert")
  (halt))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Results output

(deffunction recommend-action (?action)
  "Give final instructions to the user"
  (call JOptionPane showMessageDialog ?*frame*
        (str-cat "I recommend that you " ?action)
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
  "Given the identifier of a question, ask it"
  (declare (auto-focus TRUE))
  (MAIN::question (ident ?id) (text ?text) (valid $?valid) (type ?type))
  (not (MAIN::answer (ident ?id)))
  (MAIN::ask ?id)
  =>
  (ask-user ?text ?type ?valid)
  (engine))

(defrule ask::collect-user-input
  "Check an answer returned from the GUI, and optionally return it"
  (declare (auto-focus TRUE))
  (MAIN::question (ident ?id) (text ?text) (type ?type) (valid $?valid))
  (not (MAIN::answer (ident ?id)))
  ?user <- (user-input ?input)
  ?ask <- (MAIN::ask ?id)
  =>
  (if (is-of-type ?input ?type ?valid) then
    (retract ?ask ?user)
    (assert (MAIN::answer (ident ?id) (text ?input)))
    (return)
    else
    (retract ?ask ?user)
    (assert (MAIN::ask ?id))))

;; Main window
(defglobal ?*frame* = (new JFrame "Diagnostic Assistant"))
(?*frame* setDefaultCloseOperation (get-member JFrame EXIT_ON_CLOSE))
(?*frame* setSize 520 140)
(?*frame* setVisible TRUE)

;; Question field
(defglobal ?*qfield* = (new JTextArea 5 40))
(bind ?scroll (new JScrollPane ?*qfield*))
((?*frame* getContentPane) add ?scroll)
(?*qfield* setText "Please wait...")

;; Answer area
(defglobal ?*apanel* = (new JPanel))
(defglobal ?*afield* = (new JTextField 40))
(defglobal ?*afield-ok* = (new JButton OK))

(defglobal ?*acombo* = (new JComboBox (create$ "yes" "no")))
(defglobal ?*acombo-ok* = (new JButton OK))

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

(deffacts MAIN::question-data
  (question (ident hardware) (type multi) (valid x86 Macintosh other)
            (text "What kind of hardware is it?"))
  (question (ident sound) (type multi) (valid yes no)
            (text "Does the computer make any sound?"))
  (question (ident plugged-in) (type multi) (valid yes no)
            (text "Is the computer plugged in?"))
  (question (ident seek) (type multi) (valid yes no)
            (text "Does the disk make \"seeking\" sounds?"))
  (question (ident does-beep) (type multi) (valid yes no)
            (text "Does the computer beep?"))
  (question (ident how-many-beeps) (type number) (valid)
            (text "How many times does it beep?"))
  (question (ident loose-ram) (type multi) (valid yes no)
            (text "Are any of the memory modules loose?"))
  (question (ident boot-begins) (type multi) (valid yes no)
            (text "Does the computer begin to boot?"))
  (ask hardware))


(reset)
(run-until-halt)