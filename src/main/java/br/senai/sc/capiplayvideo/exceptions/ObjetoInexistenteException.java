package br.senai.sc.capiplayvideo.exceptions;

public class ObjetoInexistenteException extends RuntimeException{
    public ObjetoInexistenteException() {
        super("Objeto não encontrado!");
    }
}
