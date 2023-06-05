
O codigo na classe SnailShellPattern apresenta duas funções getSnailShell sendo que cada uma possui uma solução. 
A primeira função getSnailShell é feita usando uma solução single threaded que será otima para matrizes de pequena dimensão.
Já a segunda solução é feita usando multi threading que se aplica as matrizes de grande dimensão o que justifica o uso de threads. 
A escolha de uma determinada solução irá depende de varios fatores como por exemplo o tipo de hardware e processador em
que o sistema irá correr. Deste modo, a solução apresentará um bom desempenho em ambos os caso juntamente com um melhor 
escalonamento.
Neste codigo não foi considerado o tratamento de exeções por suposição que a matriz recebida é valida.

Ambas as soluções calculam a ordem por quadrado.