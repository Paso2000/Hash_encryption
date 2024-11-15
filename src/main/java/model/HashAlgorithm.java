package model;

//Estendere la pratica precedente, per proteggere i file utilizzando un algoritmo
// di digest (Hash, MAC, HMac). La pratica consente di calcolare una sintesi del
// contenuto del file con diversi algoritmi, che possono essere scelti dall'utente
// tramite il parametro corrispondente. Al file di output viene aggiunta un'intestazione,
// in cui sono memorizzate le informazioni necessarie per poter riconoscere il file e verificarne l'integrità.
// In altre parole, l'applicazione applica una sorta di vaccino al file. Per evitare la contraffazione del riepilogo,
// viene utilizzato un valore segreto che deve essere fornito dall'utente; questo valore segreto viene utilizzato come
// SEGRETO CONDIVISO o come passwd per generare una chiave segreta, a seconda dell'algoritmo scelto.

public class HashAlgorithm {
}
