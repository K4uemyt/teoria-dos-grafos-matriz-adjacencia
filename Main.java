/**
 * Trabalho Parcial 1 - Teoria dos Grafos - 2025/2
 * Autores: Kauê Michel, Rafael Bressanini
 *
 * Este programa implementa um grafo representado por uma matriz de adjacência.
 * Ele oferece funcionalidades para:
 * - Classificar o tipo do grafo
 * - Listar todas as arestas
 * - Calcular graus dos vértices e sequência de graus
 * - Executar busca em profundidade (DFS)
 *
 * Estrutura de dados principal: Matriz de adjacência (int[][])
 * onde matrizAdj[i][j] representa a quantidade de arestas de i para j.
 *
 * ----------------------------------------------------------
 * EXEMPLO DE TESTE (entrada simulada no console):
 *
 * Digite a ordem do grafo (número de vértices): 5
 * Digite a matriz de adjacência linha por linha:
 * Linha 0: 0 1 1 0 0
 * Linha 1: 1 0 0 1 0
 * Linha 2: 1 0 0 0 1
 * Linha 3: 0 1 0 0 0
 * Linha 4: 0 0 1 0 0
 *
 * Saída esperada:
 * Tipo do grafo: Não dirigido, Simples, Não regular, Não completo, Não nulo
 * Arestas do grafo: Quantidade de arestas: 4
 * Conjunto de arestas: (0,1), (0,2), (1,3), (2,4)
 * Graus do vértice:
 *   Grau do vértice 0: 2
 *   Grau do vértice 1: 2
 *   Grau do vértice 2: 2
 *   Grau do vértice 3: 1
 *   Grau do vértice 4: 1
 * Sequência de graus: 2, 2, 2, 1, 1
 * Busca em profundidade: Ordem dos vértices visitados na DFS: 0 1 3 2 4
 * ----------------------------------------------------------
*/

import java.util.*;

public class Main {

    /**
     * Determina a classificação do grafo.
     * 
     * Critérios avaliados:
     * - Dirigido ou não dirigido
     * - Simples ou multigrafo (presença de laços ou múltiplas arestas)
     * - Regular (todos os vértices têm o mesmo grau)
     * - Completo (todos os vértices conectados entre si) ou nulo (sem arestas)
     *
     * @param matrizAdj Matriz de adjacência do grafo.
     * @return String contendo a classificação completa.
    */
    public static String tipoDoGrafo(int[][] matrizAdj) {
        int n = matrizAdj.length;  // número de vértices
        boolean dirigido = false;
        boolean multigrafo = false;
        boolean regular = true;
        boolean completo = true;
        boolean nulo = true;
        int grauAnterior = -1; // usado para verificar regularidade

        // --- Verificar se é dirigido ---
        // Se existir ao menos um par (i,j) diferente de (j,i), o grafo é dirigido
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrizAdj[i][j] != matrizAdj[j][i]) {
                    dirigido = true;
                    break;
                }
            }
            if (dirigido) break;
        }

        // --- Verificar se é multigrafo (laços ou múltiplas arestas) ---
        for (int i = 0; i < n; i++) {
            if (matrizAdj[i][i] > 0) { // presença de laço
                multigrafo = true;
                break;
            }
            for (int j = (dirigido ? 0 : i+1); j < n; j++) {
                if (matrizAdj[i][j] > 1) { // múltiplas arestas
                    multigrafo = true;
                    break;
                }
            }
            if (multigrafo) break;
        }

        // --- Calcular grau de cada vértice ---
        for (int i = 0; i < n; i++) {
            int grau = 0;

            if (dirigido) {
                // Em grafos dirigidos: grau = entradas + saídas
                for (int j = 0; j < n; j++) {
                    grau += matrizAdj[i][j]; // saída
                    grau += matrizAdj[j][i]; // entrada
                }
            } else {
                // Em grafos não dirigidos: grau é a soma da linha
                for (int j = 0; j < n; j++) {
                    grau += matrizAdj[i][j];
                }
            }

            // Verifica regularidade
            if (grauAnterior == -1) {
                grauAnterior = grau;
            } else if (grau != grauAnterior) {
                regular = false;
            }

            // Verifica se é completo (cada vértice conectado a todos os outros)
            if (grau < n - 1) completo = false;

            // Verifica se é nulo (sem arestas)
            if (grau > 0) nulo = false;
        }

        // --- Monta resultado ---
        String resultado = "";
        resultado += (dirigido ? "Dirigido, " : "Não dirigido, ");
        resultado += (multigrafo ? "Multigrafo, " : "Simples, ");
        resultado += (regular ? "Regular, " : "Não regular, ");
        resultado += (completo ? "Completo, " : "Não completo, ");
        resultado += (nulo ? "Nulo" : "Não nulo");

        return resultado;
    }

    /**
     * Conta e lista todas as arestas do grafo.
     * 
     * - Se for dirigido: lista todas as arestas (i,j).
     * - Se não dirigido: lista apenas i ≤ j para evitar duplicação.
     *
     * @param matrizAdj Matriz de adjacência.
     * @return String com a quantidade e o conjunto de arestas.
    */
    public static String arestasDoGrafo(int[][] matrizAdj) {
        int n = matrizAdj.length;
        int quantidade = 0;
        StringBuilder conjuntoArestas = new StringBuilder();
        boolean dirigido = false;

        // Descobre se o grafo é dirigido
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrizAdj[i][j] != matrizAdj[j][i]) {
                    dirigido = true;
                    break;
                }
            }
            if (dirigido) break;
        }

        if (dirigido) {
            // Conta todas as arestas (direção importa)
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < matrizAdj[i][j]; k++) {
                        quantidade++;
                        conjuntoArestas.append("(").append(i).append(",").append(j).append("), ");
                    }
                }
            }
        } else {
            // Conta cada aresta apenas uma vez (i ≤ j)
            for (int i = 0; i < n; i++) {
                for (int j = i; j < n; j++) {
                    for (int k = 0; k < matrizAdj[i][j]; k++) {
                        quantidade++;
                        conjuntoArestas.append("(").append(i).append(",").append(j).append("), ");
                    }
                }
            }
        }

        // Remove última vírgula e espaço
        if (quantidade > 0) {
            conjuntoArestas.setLength(conjuntoArestas.length() - 2);
        }

        return "Quantidade de arestas: " + quantidade + "\nConjunto de arestas: " + conjuntoArestas.toString();
    }

    /**
     * Calcula graus do grafo.
     * 
     * - Grau do grafo: valor comum dos graus se regular, senão 0.
     * - Grau de cada vértice.
     * - Sequência de graus (lista dos graus em ordem).
     *
     * @param matrizAdj Matriz de adjacência.
     * @return String formatada com as informações de grau.
    */
    public static String grausDoVertice(int[][] matrizAdj) {
        int n = matrizAdj.length;
        boolean dirigido = false;

        // Descobre se é dirigido
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrizAdj[i][j] != matrizAdj[j][i]) {
                    dirigido = true;
                    break;
                }
            }
            if (dirigido) break;
        }

        int[] graus = new int[n]; // vetor de graus
        boolean regular = true;
        int grauAnterior = -1;

        // Calcula grau de cada vértice
        for (int i = 0; i < n; i++) {
            int grau = 0;
            if (dirigido) {
                int grauEntrada = 0;
                int grauSaida = 0;
                for (int j = 0; j < n; j++) {
                    grauSaida += matrizAdj[i][j];
                    grauEntrada += matrizAdj[j][i];
                }
                grau = grauEntrada + grauSaida;
            } else {
                for (int j = 0; j < n; j++) {
                    grau += matrizAdj[i][j];
                }
            }
            graus[i] = grau;

            // Verificação de regularidade
            if (grauAnterior == -1) {
                grauAnterior = grau;
            } else if (grau != grauAnterior) {
                regular = false;
            }
        }

        // Monta saída
        StringBuilder sb = new StringBuilder();
        sb.append("Grau do grafo: ").append(regular ? grauAnterior : 0).append("\n");
        for (int i = 0; i < n; i++) {
            sb.append("Grau do vértice ").append(i).append(": ").append(graus[i]).append("\n");
        }
        sb.append("Sequência de graus: ");
        for (int i = 0; i < n; i++) {
            sb.append(graus[i]);
            if (i != n - 1) sb.append(", ");
        }

        return sb.toString();
    }

    /**
     * Executa busca em profundidade (DFS).
     * 
     * Explora recursivamente todos os vértices acessíveis a partir de cada componente
     * conectado, retornando a ordem de visita.
     *
     * @param matrizAdj Matriz de adjacência.
     * @return String com a ordem dos vértices visitados.
    */
    public static String buscaEmProfundidade(int[][] matrizAdj) {
        int n = matrizAdj.length;
        boolean[] visitado = new boolean[n]; // marca vértices já visitados
        List<Integer> ordem = new ArrayList<>(); // ordem da visita

        // Garante que todos os componentes do grafo sejam explorados
        for (int i = 0; i < n; i++) {
            if (!visitado[i]) {
                dfs(i, matrizAdj, visitado, ordem);
            }
        }

        // Monta resultado
        StringBuilder sb = new StringBuilder();
        sb.append("Ordem dos vértices visitados na DFS: ");
        for (int v : ordem) {
            sb.append(v).append(" ");
        }
        return sb.toString().trim();
    }

    /**
     * Função recursiva auxiliar da DFS.
     *
     * @param v Vértice atual.
     * @param matrizAdj Matriz de adjacência.
     * @param visitado Vetor de controle de visitados.
     * @param ordem Lista com ordem dos vértices visitados.
    */
    private static void dfs(int v, int[][] matrizAdj, boolean[] visitado, List<Integer> ordem) {
        visitado[v] = true;
        ordem.add(v);
        for (int i = 0; i < matrizAdj.length; i++) {
            // Só segue para vizinhos conectados não visitados
            if (matrizAdj[v][i] > 0 && !visitado[i]) {
                dfs(i, matrizAdj, visitado, ordem);
            }
        }
    }

    /**
     * Método principal para execução interativa.
     * Lê a ordem do grafo e a matriz de adjacência a partir do teclado
     * e imprime os resultados das análises.
    */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Entrada de dados
        System.out.print("Digite a ordem do grafo (número de vértices): ");
        int n = sc.nextInt();
        int[][] matrizAdj = new int[n][n];

        System.out.println("Digite a matriz de adjacência linha por linha:");
        for (int i = 0; i < n; i++) {
            System.out.print("Linha " + i + ": ");
            for (int j = 0; j < n; j++) {
                matrizAdj[i][j] = sc.nextInt();
            }
        }

        // Saída com resultados
        System.out.println("\nResultados com a matriz fornecida:");
        System.out.println("Tipo do grafo: " + tipoDoGrafo(matrizAdj));
        System.out.println("Arestas do grafo: " + arestasDoGrafo(matrizAdj));
        System.out.println("Graus do vértice: " + grausDoVertice(matrizAdj));
        System.out.println("Busca em profundidade: " + buscaEmProfundidade(matrizAdj));

        sc.close();
    }
}
