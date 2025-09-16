# Teoria dos Grafos - Matriz de Adjacência  

Este projeto foi desenvolvido como parte do **Trabalho Parcial 1** da disciplina de **Teoria dos Grafos** do curso de **Sistemas de Informação da FURB (Universidade Regional de Blumenau)**.  

O objetivo da atividade é implementar, em Java, um grafo representado por **matriz de adjacência**, permitindo realizar diversas análises estruturais e algoritmos clássicos relacionados a grafos.  

---

## 📌 Sobre o Projeto  

O programa trabalha com a representação de grafos via **matriz de adjacência** (`int[][]`), onde cada posição `matrizAdj[i][j]` indica a quantidade de arestas entre os vértices `i` e `j`.  

Ele oferece funcionalidades para:  
- Classificar o tipo do grafo (dirigido, simples, multigrafo, regular, completo, nulo).  
- Listar todas as arestas presentes no grafo.  
- Calcular os graus de cada vértice e a sequência de graus do grafo.  
- Executar a **busca em profundidade (DFS)**, mostrando a ordem em que os vértices são visitados.  

---

## 🧮 Estrutura do Projeto  

O projeto é composto por métodos principais:  

- `tipoDoGrafo(matrizAdj)` → Identifica e retorna a classificação do grafo.  
- `arestasDoGrafo(matrizAdj)` → Lista as arestas e a quantidade total.  
- `grausDoVertice(matrizAdj)` → Calcula os graus de cada vértice e a sequência de graus.  
- `buscaEmProfundidade(matrizAdj)` → Realiza a DFS e mostra a ordem dos vértices explorados.  

---

## ▶️ Executando o Projeto  

1. Compile os arquivos `.java`:  
   ```bash
   javac Main.java
Execute o programa:

java Main
O programa solicitará a ordem do grafo (número de vértices) e a matriz de adjacência.

# 📥 Exemplo de Entrada
Digite a ordem do grafo (número de vértices): 5
Digite a matriz de adjacência linha por linha:
Linha 0: 0 1 1 0 0
Linha 1: 1 0 0 1 0
Linha 2: 1 0 0 0 1
Linha 3: 0 1 0 0 0
Linha 4: 0 0 1 0 0

# 📤 Exemplo de Saída
Tipo do grafo: Não dirigido, Simples, Não regular, Não completo, Não nulo
Arestas do grafo: Quantidade de arestas: 4
Conjunto de arestas: (0,1), (0,2), (1,3), (2,4)
Graus do vértice:
  Grau do vértice 0: 2
  Grau do vértice 1: 2
  Grau do vértice 2: 2
  Grau do vértice 3: 1
  Grau do vértice 4: 1
Sequência de graus: 2, 2, 2, 1, 1
Busca em profundidade: Ordem dos vértices visitados na DFS: 0 1 3 2 4

# 👥 Autores
Kauê Michel Reblin Kloth
Rafael Bressanini
