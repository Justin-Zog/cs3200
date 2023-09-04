// Justin Herzog, Sep 3 2023

import kotlin.random.Random


fun main() {
    montyHallSim()
    montyHallSim(changeDoor = true)
    prisonersProblemSim(prisoners = 1000)
    prisonersProblemSim(prisoners = 1000, isRandomBox = false)
}


fun montyHallSim(runs: Int = 1000, changeDoor: Boolean = false) {
    var successes = 0
    val changeDoorStr = if (changeDoor) "switches" else "does not switch"

    for (i in 0 until runs) {
        // Setup
        val correctDoor = Random.nextInt(3)
        var contestentDoor = Random.nextInt(3)

        // Generates a random number that is not the correctDoor's or the contestentDoor's number.
        val revealedDoor = (0..2).filter {it != correctDoor && it != contestentDoor}.random()

        // Changes the door if changeDoor is true
        if (changeDoor) {
            contestentDoor = (0..2).filter {it != contestentDoor && it != revealedDoor}.random()
        }

        // Checks for success
        if (contestentDoor == correctDoor) successes += 1
    }

    // Prints the results
    println("-- Monty Hall Simulation (Contestent $changeDoorStr doors)")
    println("Number of simulations run: $runs")
    println("Number of success: $successes")
    println("Probability of success: ${(successes/runs.toDouble())*100}%\n")
}


fun prisonersProblemSim(runs: Int = 10000, prisoners: Int = 100, isRandomBox: Boolean = true) {
    var successes = 0
    var methodStr = if (isRandomBox) "Random box" else "Matching number"

    for (h in 0 until runs) {
        // Setup
        val boxes = Array<Int>(prisoners) { pos -> pos}
        var foundNum = 0
        boxes.shuffle()

        // Runs each prisoner
        for (i in 0 until prisoners) {
            // Checks to see if a prisoner failed
            if (foundNum != i) break
            var choiceBox = i

            for (j in 0 until (prisoners/2)) {
                if (isRandomBox) {
                    val chosenNumbers = Array<Int>(prisoners/2) { _ -> -1}
                    val randBox = (0 until prisoners).filter { !chosenNumbers.contains(it) }.random()
                    if (boxes[randBox] == i) {
                        foundNum += 1
                        break
                    }
                }
                else {

                    if (boxes[choiceBox] == i) {
                        foundNum += 1
                        break
                    }
                    else {
                        choiceBox = boxes[choiceBox]
                    }
                }
            }

        }
        if (foundNum == prisoners) successes += 1
    }

    // Prints the results
    println("-- $prisoners Prisoners Simulation ($methodStr selection)")
    println("Number of simulations run: $runs")
    println("Number of successes: $successes")
    println("Probability of success: ${(successes/runs.toDouble())*100}%\n")
}
