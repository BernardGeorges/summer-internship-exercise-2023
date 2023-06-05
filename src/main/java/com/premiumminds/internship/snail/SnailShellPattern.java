package com.premiumminds.internship.snail;

import java.util.concurrent.*;


/*
  Nota: Optei por fazer uma solução threaded para que seja mais facil o aumento da escala da matriz.
        Apesar de em matrizes pequenas esta solucao possa nao ter muitas vantagens de tempo em matrizes maiores será
        melhor
 */

/**
 * Created by aamado on 05-05-2023.
 */
class SnailShellPattern implements ISnailShellPattern {

  private int[] res;

  /**
   * Method to calculate the snailshell pattern using a single thread. This method is intended for small data matrix.
   *
   * @param matrix matrix of numbers to go through
   * @return order array of values thar represent a snail shell pattern
   */
  private int[] snailShellCalc(int[][] matrix){
    //delta - é o offset usado para nao ler pontos previamente lidos
    // x / y - coordenadas por ser lidas
    // as variaveis change serão as variaveis que indicam a direção em que a funcao esta a ler caso ele seja 1 a direção será de cima pra baixo e direita pra esquerda e vice-versa
    // pos será a posição do array resolucao em que a funcao deve escrever.

    int i=0, len = matrix.length,delta = 0, x=0,y=0,changeX=1, changeY=1;
    int[] res = new int[len*len];

    //verifica a escrita de todos os pontos da matriz

    while(i< len*len){

      //Ciclo para percorrer as linhas

      while((x<len-delta&&changeX==1)||(delta-1<=x&&changeX==-1)){
        res[i] = matrix[y][x];
        x += changeX;
        i++;
      }

      // alteração do sentido do ciclo

      changeX *= -1;

      //reiniciação das variaveis x e y a uma posição valida pois apos a conclusao do ciclo estas estarao of bounds
      x += changeX;
      y += changeY;

      //processo semelhante ao de cima porém para a variavel y. Lendo assim as colunas desejadas

      while((y<len-delta&&changeY==1)||(delta<=y&&changeY==-1)){
        res[i] = matrix[y][x];
        y += changeY;
        i++;
      }

      //como ja foi percorrido uma secção é aumentado o delta para que não seja lido novamente os pontos previamente lidos

      if(changeY == 1){
        delta++;
      }
      changeY *= -1;
      y += changeY;
      x += changeX;
    }
    return res;
  }


  /**
   * Method to calculate the snailshell pattern using multiple threads. This method is intended for big data matrix.
   *
   * @param matrix matrix of numbers to go through
   * @param start position in with the search will start. the coordenates will be (start,start) as it is a quadracular matrix
   * @return order array of values thar represent a snail shell pattern
   */
  private int[] snailShellThreaded(int[][] matrix,int start){
    //delta - é o offset usado para nao ler pontos previamente lidos
    // x / y - coordenadas por ser lidas
    // as variaveis change serão as variaveis que indicam a direção em que a funcao esta a ler caso ele seja 1 a direção será de cima pra baixo e direita pra esquerda e vice-versa
    // pos será a posição do array resolucao em que a funcao deve escrever.
    // funcao auxiliar para calculos e posteriormente a quantidade de pontos que a função deve escrever

    int i=0, len = matrix.length,delta = start, x=start,y=start,changeX=1, changeY=1, pos = 0, aux;

    // calculo da posição inicial de escrita no array

    for(int j=0; j<start; j++){
      aux = len - (j*2);
      pos += (aux*2)+((aux-2)*2);
    }

    //calculo de quantos elementos deve escrever

    aux = len - (start*2);
    aux = (aux*2)+((aux-2)*2);

    // verifica se ja foram escrito todos os pontos

    while(i< aux){

      //escrita dos valores nas linhas primeiramente ler de esquerda pra direita depois faz o inverso

      while((x<len-delta&&changeX==1)||(delta-1<=x&&changeX==-1)){
        this.res[pos+i] = matrix[y][x];
        x += changeX;
        i++;
      }
      changeX *= -1;
      x += changeX;
      y += changeY;

      // semelhante ao de cima para as colunas desejadas

      while((y<len-delta&&changeY==1)||(delta<=y&&changeY==-1)){
        res[pos+i] = matrix[y][x];
        y += changeY;
        i++;
      }
      delta++;

      // altera o change assim na sua proxima iteração o while acima irá correr na direção contraria

      changeY *= -1;
      y += changeY;
      x += changeX;
    }
    return res;
  }

  /**
   * Method to get snailshell pattern using a single thread - for small matrixes
   *
   * @param matrix matrix of numbers to go through
   * @return order array of values thar represent a snail shell pattern
   */

  public Future<int[]> getSnailShell(int[][] matrix) {
    //execução da solução sem threads
      ExecutorService executor = Executors.newSingleThreadExecutor();
      return executor.submit(() -> snailShellCalc(matrix));
  }



  /**
   * Method to get snailshell pattern
   *
   * @param matrix matrix of numbers to go through
   * @return order array of values thar represent a snail shell pattern
   */
  /*
  public Future<int[]> getSnailShell(int[][] matrix) {

    //execução da solução com threads - para matrizes big data

    // criação da matrix utilizada

    this.res = new int[matrix.length* matrix.length];
    ExecutorService executor = Executors.newCachedThreadPool();

    // o center é a coordenada do ponto central. Como á matriz tem um tamanho simetrico o seu centro tambem terá coordenadas simetricas

    int center = matrix.length/2;
    Future<int[]> futureThreaded = new CompletableFuture<>();

    // caso da matriz ser impar poupa-se uma thread para o centro colocando-o automaticamente no ultimo ponto

    if((matrix.length%2) == 1){
      this.res[(matrix.length* matrix.length)-1] = matrix[center][center];
    }

    //criação das threads
    //cada thread ira calcular uma camada da matriz começando na posição (i,i) e percorrendo a sua camada

    for (int i=0; i<center; i++){
        final int x = i;
        futureThreaded = executor.submit(() ->snailShellThreaded(matrix, x));
    }

    return futureThreaded;
  }
  */

}
