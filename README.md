# Distribuirani algoritmi za pronalazak najkraćeg puta

Postoje različite verzije rješavanja problema najkraćih puteva. Ovaj rad fokusiran je na distribuirane varijante Bellman-Fordovog odnosno Floyd–Warshallovog algoritma.
Varijante se razlikuju po tome što pretpostavljaju sinkronu ili asinkronu komunikaciju. Svaka od navedenih varijanti pretpostavlja postojanje jednog procesa u svakom
čvoru grafa, a procesi mogu medusobno komunicirati samo preko bridova u grafu. Dakle, nijedan proces nema punu informaciju o grafu, već jedino zna tko su mu susjedi i kolike
su težine bridova do tih susjeda.

Izradili: Dominik Mikulčić i Petra Škrabo
