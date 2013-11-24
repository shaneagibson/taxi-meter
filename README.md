
taxi fare calculator
=======

Implementation
--------------

The implementation assumes a 1/10th of a second poll mechanism that commences when the journey is started, and
terminated when the journey is completed.

This ensures that there is no need to apply recursion or iteration across multiple time or distance ranges, since
a typical London Taxi doesn't reach speeds of 3211.2 km per hour (89.2 meters per tenth of a second being the smallest
distance range at which the rate is incremented).

The key class for the algorithm is uk.co.epsilontechnologies.taximeter.calculator.FareCalculator

Each update stores the calculated fare against the distance and time that have been accounted for (already paid for).

In addition to implementing the algorithm, the tariffs, and the tariff lookup mechanism, I have also provided a
Taxi Meter implementation to show how this might be applied, and how polling might be achieved.

Requirements
------------

The meter automatically adds a charge based on time for any part of the journey when the speed drops below 10.4mph. Other extras may be included in the final fare.

Tariff 1

For any hiring during Monday to Friday other than on a public holiday between 06:00 and 20:00

For the first 254.6 metres or 54.8 seconds (whichever is reached first) there is a minimum charge of £2.40
For each additional 127.3 metres or 27.4 seconds (whichever is reached first), or part thereof, if the fare is less than £17.20 then there is a charge of 20p
Once the fare is £17.20 or greater then there is a charge of 20p for each additional 89.2 metres or 19.2 seconds (whichever is reached first), or part thereof
Tariff 2

For any hiring either during Monday to Friday between 20:00 and 22:00 or during Saturday or Sunday between 06:00 and 22:00, other than on a public holiday:

For the first 206.8 metres or 44.4 seconds (whichever is reached first) there is a minimum charge of £2.40
For each additional 103.4 metres or 22.2 seconds (whichever is reached first), or part thereof, if the fare is less than £20.80 there is a charge of 20p
Once the fare is £20.80 or greater then there is a charge of 20p for each additional 89.2 metres or 19.2 seconds (whichever is reached first), or part thereof
Tariff 3

For any hiring between 22:00 on any day and 06:00 the following day or at any time on a public holiday

For the first 166.8 metres or 35.8 seconds (whichever is reached first) there is a minimum charge of £2.40
For each additional 83.4 metres or 17.9 seconds (whichever is reached first), or part thereof, if the fare is less than £25.20 there is a charge of 20p
Once the fare is £25.20 or greater then there is a charge of 20p for each additional 89.2 metres or 19.2 seconds (whichever is reached first)
If a journey goes through more than one tariff, the new charge will be applied from the start time of the new tariff.

All taxi fares and tariffs information shown on these pages is effective from Saturday 6 April 2013.

Source: http://www.tfl.gov.uk/gettingaround/taxisandminicabs/taxis/taxifares/4870.aspx


