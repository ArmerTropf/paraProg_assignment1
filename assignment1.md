# ParallelProg Assignment 1

- Hendrik Mahrt ; 33040
- Andre Schriever ; 32868
- Michael Günster ; 32465


## zu 1.1b
### Antwort
Nein, die Wrapperklasse verhindert nicht, dass gleichzeitig auf den Counter zugegriffen wird. 
Die Operation ist immer noch nicht atomar.

## zu 1.1c
### Antwort
Ja, da AtomicLong intern für eine atomarität der Operation sorgt.

## zu 1.1d
Führt man die Modulooperation ohne die gegebene Funktion accumulateAndGet() aus, ist sie nicht mehr atomar und 
führt dann zu Synchronisationsproblemen beim Zählen.


## zu 1.2b
### Antwort
Wir konnten keine großen Erkenntnisse gewinnen, außer, dass der default-Pool
so groß ist, wie auch logische Kerne vorhanden sind.



