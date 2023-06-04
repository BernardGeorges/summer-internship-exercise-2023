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

  private int[] snailShellThreaded(int[][] matrix,int start){
    //delta - sera a os pontos das linhas que ja foram lidos previamente
    // x / y - sao as coordenadas que vao ser lidas
    // as variaveis change serão as variaveis que indicam a direção em que a funcao esta a ler
    // pos será a posição do array resolucao em que a funcao tem que começar
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
   * Method to get snailshell pattern
   *
   * @param matrix matrix of numbers to go through
   * @return order array of values thar represent a snail shell pattern
   */
  public Future<int[]> getSnailShell(int[][] matrix) {
    // criação da matrix utilizada
    this.res = new int[matrix.length* matrix.length];
    ExecutorService executor = Executors.newCachedThreadPool();
    // o center é a coordenada do ponto central. Como á matriz tem um tamanho simetrico o seu centro tambem terá coordenadas simetricas
    int center = matrix.length/2;
    Future<int[]> ret = new CompletableFuture<>();
    // caso da matriz ser impar poupa-se uma thread para o centro colocando-o automaticamente no ultimo ponto
    if((matrix.length%2) == 1){
      this.res[(matrix.length* matrix.length)-1] = matrix[center][center];
    }
    //criação das threads
    //cada thread ira calcular uma camada da matriz começando na posição (i,i) e percorrendo a sua camada
    for (int i=0; i<center; i++){
        final int x = i;
        ret = executor.submit(() ->snailShellThreaded(matrix, x));
    }
    // os mods serao as variaveis utilizadas para alterar os valores de X e Y ao percorrer a matrix
    return ret;
  }
}
