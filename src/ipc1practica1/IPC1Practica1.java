/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ipc1practica1;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Bryan
 */
public class IPC1Practica1 {

    /**
     * @param args the command line arguments
     */
    public static String[][] tablero = new String[8][8];
    public static Boolean[][] coordenadaPenalizacion = new Boolean[8][8];
    public static int coordenadaJugadorX = 8;
    public static int coordenadaJugadorY = 7;
    public static int nCasillasAvanzadas = 0;
    public static boolean jugadorVivo = true;
    
    public static Scanner leerData = new Scanner(System.in);

    public static void main(String[] args) {
        // TODO code application logic here

        boolean ejecucion = true;
        while (ejecucion) {
            
            int opcionSeleccionada = opcionMenu();
            
            switch (opcionSeleccionada) {
                
                case 1, 2:

                    if (opcionSeleccionada == 1) { //Iniciar el juego
                        
                        System.out.println("------Bienvenido------");
                        //Definimos por determinado las posiciones
                        coordenadaJugadorX = 8;
                        coordenadaJugadorY = 7;
                        nCasillasAvanzadas = 0;
                        jugadorVivo = true;
                        
                        generarTablero();
                        mostrarTablero();
                        
                       
                    }
                    else if (opcionSeleccionada == 2 && jugadorVivo) 
                    { //Validamos si desea continuar el juego
                        System.out.println("Continuando");
                        tablero[coordenadaJugadorY][coordenadaJugadorX] += "@";
                        mostrarTablero();
                        tablero[coordenadaJugadorY][coordenadaJugadorX] = tablero[coordenadaJugadorY][coordenadaJugadorX].substring(0, tablero[coordenadaJugadorY][coordenadaJugadorX].length() - 1);
                    
                    } else { 
                        System.out.println("Has perdido el juego");
                        break;
                    }

                    boolean ejecucionDado = true;
                    String datoLeido;
                    leerData = new Scanner(System.in);
                    do {
                        if (jugadorVivo) { //Validmos si el jugador esta vivo

                            System.out.println("¿Desseas lanzar el dado? \n 1. Si  2. No");
                            try {

                                datoLeido = leerData.nextLine();
                                if (datoLeido.toLowerCase().contains("p")) {
                                    ejecucionDado = false;
                                    System.out.println("Juego Pausado");
                                } else {
                                    opcionSeleccionada = Integer.parseInt(datoLeido);
                                    if (opcionSeleccionada == 1) {
                                        Random random = new Random();
                                        // Generar un número aleatorio entre 1 y 6 (inclusive)
                                        int numeroDado = random.nextInt(6) + 1;
                                        nCasillasAvanzadas += numeroDado;
                                        System.out.println("El jugador avanzo: " + numeroDado + " Posiciones");
                                        if (nCasillasAvanzadas <= 64) {
                                            avanzarJugador(numeroDado);
                                            validarTipoProblema();
                                        } else {
                                            System.out.println("Haz ganado el juego");
                                            ejecucionDado = false;
                                        }

                                    } else {
                                        ejecucionDado = false;
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Error intente nueevamente");
                                System.out.println(e);
                            }
                        } else {
                            ejecucionDado = false;
                        }
                    } while (ejecucionDado);

                    break;
                    
                case 3: 
                    System.out.println("Nombre Completo: Bryan Josue Coronado Lainez");
                    System.out.println("Carnet: 202209258");
                    System.out.println("Seccion: F");
                    break;
                    
                case 4:
                    System.out.println("Saliendo..");
                    ejecucion=false;
                    break; 
                   
                default:
                    System.out.println("Opcion invalida, intente nuevamente");
                    break;
            }

        }
    }

    public static void generarTablero() {
        int nPosiciones = 64;
        for (int i = 0; i < 8; i++) {
            int nPenalizaciones = 0;
            if (i % 2 == 0) {
                //Verifica si es la primera fila
                if (i == 0) {
                    nPosiciones = nPosiciones - 8;
                } else {
                    nPosiciones = nPosiciones - 9;
                }
            } else {
                nPosiciones = nPosiciones - 7;
            }
            for (int j = 0; j < 8; j++) {
                // Genera un número aleatorio entre 0 y 1
                double numeroAleatorio = Math.random();
                boolean generarPenalizacion = numeroAleatorio >= 0.7; //Probabilidad que tenga penalizacion
                if (i % 2 != 0) {
                    nPosiciones--;
                } else {
                    nPosiciones++;
                }
                
                if (generarPenalizacion && nPenalizaciones < 4) {
                    tablero[i][j] = nPosiciones + "#";
                    coordenadaPenalizacion[i][j] = true;
                    nPenalizaciones++;

                } else {
                    tablero[i][j] = nPosiciones + "";
                    coordenadaPenalizacion[i][j] = false;
                }
            }
        }
    }

    public static void mostrarTablero() {
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("|  " + (tablero[i][j]) + "    |");
            }
            System.out.println("");
        }
    }

    public static int opcionMenu() {

        byte opcionSeleccionda = 0;
        boolean validacion = false;
        do {
            System.out.println("1. Iniciar juego");
            System.out.println("2. Retomar juego");
            System.out.println("3. Mostrar infomormacion del estudiante");
            System.out.println("4. Salir");
            try {
                
                opcionSeleccionda = leerData.nextByte();
                if (opcionSeleccionda > 0 && opcionSeleccionda < 5) {
                    validacion = true;
                } else {
                    System.out.println("Error opcion incorrecta");
                }
            } catch (Exception e) {
                System.out.println("Dato invalido, intetalo nuevamente");
            }
        } while (!validacion);

        return opcionSeleccionda;
    }

    public static void avanzarJugador(int nPasos) {
        if (coordenadaJugadorY % 2 != 0) {
            if ((coordenadaJugadorX - nPasos) >= 0) {
                coordenadaJugadorX = coordenadaJugadorX - nPasos;
            } else {
                coordenadaJugadorX = Math.abs((coordenadaJugadorX - nPasos + 1));
                if (coordenadaJugadorY >= 1) {
                    coordenadaJugadorY--;
                } else {
                    System.out.println("Se excedio y");
                }
            }
        } else {
            System.out.println("fila 1");
            if ((coordenadaJugadorX + nPasos) < 8) {

                coordenadaJugadorX = coordenadaJugadorX + nPasos;
            } else {

                coordenadaJugadorX = (8 - Math.abs(7 - (coordenadaJugadorX + nPasos)));

                if (coordenadaJugadorY >= 1) {

                    coordenadaJugadorY--;

                } else {
                    System.out.println("Se excedio y");
                }
            }
        }

        tablero[coordenadaJugadorY][coordenadaJugadorX] += "@";
        mostrarTablero();
        tablero[coordenadaJugadorY][coordenadaJugadorX] = tablero[coordenadaJugadorY][coordenadaJugadorX].substring(0, tablero[coordenadaJugadorY][coordenadaJugadorX].length() - 1);

    }

    public static void validarTipoProblema() {
        if (coordenadaPenalizacion[coordenadaJugadorY][coordenadaJugadorX]) {
            System.out.println("Has caido en una penalizacion!");

            if (coordenadaJugadorY >= 6) {
                System.out.println("Operacion: facil");
                //problema basico
                generarProblemaBasico();

            } else if (coordenadaJugadorY >= 3) {
                System.out.println("Operacion: intermedia");
                //Problema intermedio
                generarProblemaIntermedio();

            } else if (coordenadaJugadorY >= 0) {
                System.out.println("Operacion: dificil");
                //Problema dificil
                generarProblemaAvanzado();
            }
        }

    }

    public static void generarProblemaBasico() {
        Boolean[] problemaMostrado = {false, false, false};
        Random random = new Random();
        // Generar un número aleatorio entre 0 (inclusive) y 3 (exclusive)
        int numeroAleatorio = random.nextInt(3);
        System.out.println("Tema: Ley de cosenos");
        double lado,
                anguloPrimario,
                anguloSecundario;
        switch (numeroAleatorio) {
            case 0:

                if (!problemaMostrado[0]) {
                    System.out.println("Lado A: 15");
                    System.out.println("Lado C: 20");
                    System.out.println("angulo α: 25");
                    System.out.println("Encontrar los valores del lado B y los angulos beta y theta (Aproximar a 3 decimales)");
                    System.out.println("Ingresa el valor del lado B:");
                    lado = leerData.nextDouble();
                    System.out.println("Ingresa el valor del  Beta:");
                    anguloPrimario = leerData.nextDouble();
                    System.out.println("Ingresa el valor del angulo Theta:");
                    anguloSecundario = leerData.nextDouble();
                    double[] respuestas = calcularLeyDeCosenos(15, 20, 25);
                    validarRespuestasProblemaBasico(respuestas, lado, anguloPrimario, anguloSecundario);
                    break;
                }
            case 1:
                if (!problemaMostrado[1]) {
                    System.out.println("Lado A: 10");
                    System.out.println("Lado C: 25");
                    System.out.println("angulo b: 30 ");
                    System.out.println("Encontrar los valores del lado A y los ángulos alpha y theta (Aproximar a 3 decimales)");
                    System.out.println("Ingresa el valor del lado A:");
                    lado = leerData.nextDouble();
                    System.out.println("Ingresa el valor del  Alpha:");
                    anguloPrimario = leerData.nextDouble();
                    System.out.println("Ingresa el valor del angulo Theta:");
                    anguloSecundario = leerData.nextDouble();
                    double[] respuestas = calcularLeyDeCosenos(10, 25, 30);
                    validarRespuestasProblemaBasico(respuestas, lado, anguloPrimario, anguloSecundario);
                    break;
                }
            case 2:
                if (!problemaMostrado[2]) {
                    System.out.println("Lado A: 18");
                    System.out.println("Lado C: 25");
                    System.out.println("angulo y: 30 ");
                    System.out.println("Encontrar los valores del lado C y los ángulos alpha y beta (Aproximar a 3 decimales)");
                    System.out.println("Ingresa el valor del lado C:");
                    lado = leerData.nextDouble();
                    System.out.println("Ingresa el valor del  Alpha:");
                    anguloPrimario = leerData.nextDouble();
                    System.out.println("Ingresa el valor del angulo Beta:");
                    anguloSecundario = leerData.nextDouble();
                    double[] respuestas = calcularLeyDeCosenos(18, 25, 30);
                    validarRespuestasProblemaBasico(respuestas, lado, anguloPrimario, anguloSecundario);
                    break;
                }
            default:
                System.out.println("Todos los problemas ya fueron resueltos");
                break;
        }

        problemaMostrado[numeroAleatorio] = true;

        System.out.println("Número aleatorio: " + numeroAleatorio);
    }

    public static double[] calcularLeyDeCosenos(double ladoPrincipal, double ladoSecundario, double anguloCalcular) {
        double[] resultados = new double[3];
        double angulo = Math.toRadians(anguloCalcular);
        // Calcular el lado A usando la Ley de Cosenos
        double lado = Math.sqrt(Math.pow(ladoPrincipal, 2) + Math.pow(ladoSecundario, 2) - 2 * ladoPrincipal * ladoSecundario * Math.cos(angulo));
        lado = Math.round(lado * 1000.0) / 1000.0;
        // Calcular los ángulos α y γ usando la Ley de Cosenos
        double anguloPrimario = Math.acos((Math.pow(ladoPrincipal, 2) + Math.pow(lado, 2) - Math.pow(ladoSecundario, 2)) / (2 * ladoPrincipal * lado));
        double anguloSecundario = Math.acos((Math.pow(ladoSecundario, 2) + Math.pow(lado, 2) - Math.pow(ladoPrincipal, 2)) / (2 * ladoSecundario * lado));

        // Convertir los ángulos de radianes a grados
        anguloPrimario = Math.toDegrees(anguloPrimario);
        anguloSecundario = Math.toDegrees(anguloSecundario);

        anguloPrimario = Math.round(anguloPrimario * 1000.0) / 1000.0;
        anguloSecundario = Math.round(anguloSecundario * 1000.0) / 1000.0;

        resultados[0] = lado;
        resultados[1] = anguloPrimario;
        resultados[2] = anguloSecundario;
        // Imprimir los resultados
        //System.out.println("Lado : " + lado);
        //System.out.println("Ángulo 1: " + anguloPrimario);
        //System.out.println("Ángulo 2: " + anguloSecundario);

        return resultados;
    }

    public static void validarRespuestasProblemaBasico(double[] respuestas, double lado, double anguloPrimario, double anguloSecundario) {

        int contadorBuenas = 0;

        if (respuestas[0] == lado) {
            contadorBuenas++;
        }
        if (respuestas[1] == anguloPrimario) {
            contadorBuenas++;
        }
        if (respuestas[2] == anguloSecundario) {
            contadorBuenas++;
        }

        if (contadorBuenas < 3) {
            jugadorVivo = false;
            System.out.println("FIN DEL JUEGO");
            System.out.println("Tienes " + contadorBuenas + " de 3");
            System.out.println("Las respuestas correctas eran: ");
            System.out.println("Lado: " + respuestas[0]);
            System.out.println("Angulo 1: " + respuestas[1]);
            System.out.println("Angulo 2; " + respuestas[2]);
        }else{
            System.out.println("Haz logrado vencer la penalizacion, continua tú camino ");
        }

    }

    public static void generarProblemaIntermedio() {
        Boolean[] problemaMostrado = {false, false, false};
        Random random = new Random();

        // Generar un número aleatorio entre 0 (inclusive) y 3 (exclusive)
        int numeroAleatorio = random.nextInt(3);
        System.out.println("Tema: Suma y resta de matrices");

        switch (numeroAleatorio) {
            case 0:
                if (!problemaMostrado[0]) {
                    double[][] matrizA = {
                        {7, 48, 5, 0, 1},
                        {57, 8, 4, 6, 14},
                        {0, 5, 6, 78, 15},
                        {21, 14, 8, 19, 54},
                        {32, 20, 26, 47, 12}
                    };

                    double[][] matrizB = {
                        {9, 5, 2, 1, 8},
                        {4, 2, 3, 47, 8},
                        {48, 55, 32, 19, 6},
                        {7, 56, 32, 14, 8},
                        {32, 87, 0, 1, 7}
                    };

                    //llenar matriz
                    System.out.println("Calcular la suma de las matrices:");
                    System.out.println("------------------------");
                    System.out.println("Matriz A");
                    imprimirMatriz(matrizA);
                    System.out.println("Matriz B");
                    imprimirMatriz(matrizB);
                    double[][] resultadoSuma = sumarMatrices(matrizA, matrizB);
                    double[][] respuestasUsuario = ingresarMatriz("Resultante", 5, 5);

                    System.out.println("");
                    System.out.println("Tu matriz ingresada es:");
                    imprimirMatriz(respuestasUsuario);
                    if (sonMatricesIguales(resultadoSuma, respuestasUsuario)) {
                        System.out.println("¡Respuesta correcta!");
                        System.out.println("Haz logrado vencer la penalizacion, continua tú camino ");
                        System.out.println("La respuesta es:");
                        imprimirMatriz(resultadoSuma);
                    } else {
                        jugadorVivo = false;
                        System.out.println("FIN DEL JUEGO");
                        System.out.println("La respuesta correcta es: ");
                        imprimirMatriz(resultadoSuma);
                    }
                    break;
                }
            case 1:
                if (!problemaMostrado[1]) {
                    double[][] matrizA = {
                        {4, 9, 7, 45, 18},
                        {7, 51, 26, 8, 38},
                        {48, 26, 37, 21, 19},
                        {1, 0, 6, 8, 1},
                        {2, 19, 55, 25, 15}
                    };

                    double[][] matrizB = {
                        {0, 2, 15, 1, 66},
                        {21, 48, 62, 7, 33},
                        {4, 88, 0, 68, 4},
                        {25, 18, 24, 7, 55},
                        {24, 15, 36, 5, 98}
                    };

                    //llenar matriz
                    System.out.println("Calcular la suma de las matrices:");
                    System.out.println("------------------------");
                    System.out.println("Matriz A");
                    imprimirMatriz(matrizA);
                    System.out.println("Matriz B");
                    imprimirMatriz(matrizB);
                    double[][] resultadoSuma = sumarMatrices(matrizA, matrizB);
                    double[][] respuestasUsuario = ingresarMatriz("Resultante", 5, 5);

                    System.out.println("");
                    System.out.println("Tu matriz ingresada es:");
                    imprimirMatriz(respuestasUsuario);
                    if (sonMatricesIguales(resultadoSuma, respuestasUsuario)) {
                        System.out.println("¡Respuesta correcta!");
                        System.out.println("Haz logrado vencer la penalizacion, continua tú camino ");
                        System.out.println("La respuesta es:");
                        imprimirMatriz(resultadoSuma);
                    } else {
                        jugadorVivo = false;
                        System.out.println("FIN DEL JUEGO");
                        System.out.println("La respuesta correcta es: ");
                        imprimirMatriz(resultadoSuma);
                    }
                    break;
                }
            case 2:
                if (!problemaMostrado[2]) {
                    double[][] matrizA = {
                        {0, 1, 15, 5, 36},
                        {1, 78, 65, 32, 4},
                        {48, 66, 39, 0, 55},
                        {14, 98, 63, 20, 15},
                        {11, 39, 84, 7, 1}
                    };

                    double[][] matrizB = {
                        {78, 25, 66, 48, 98},
                        {0, 45, 2, 3, 1},
                        {2, 9, 14, 10, 20},
                        {35, 87, 65, 2, 32},
                        {25, 8, 4, 9, 39}
                    };

                    //llenar matriz
                    System.out.println("Calcular la suma de las matrices:");
                    System.out.println("------------------------");
                    System.out.println("Matriz A");
                    imprimirMatriz(matrizA);
                    System.out.println("Matriz B");
                    imprimirMatriz(matrizB);
                    double[][] resultadoSuma = sumarMatrices(matrizA, matrizB);
                    double[][] respuestasUsuario = ingresarMatriz("Resultante", 5, 5);

                    System.out.println("");
                    System.out.println("Tu matriz ingresada es:");
                    imprimirMatriz(respuestasUsuario);
                    if (sonMatricesIguales(resultadoSuma, respuestasUsuario)) {
                        System.out.println("¡Respuesta correcta!");
                        System.out.println("Haz logrado vencer la penalizacion, continua tú camino ");
                        System.out.println("La respuesta es:");
                        imprimirMatriz(resultadoSuma);
                    } else {
                        jugadorVivo = false;
                        System.out.println("FIN DEL JUEGO");
                        System.out.println("La respuesta correcta es: ");
                        imprimirMatriz(resultadoSuma);
                    }
                    break;
                }
            default:
                System.out.println("Se llego el maximo de problemas resueltos");
                break;
        }

    }

    private static double[][] ingresarMatriz(String nombre, int filas, int columnas) {
        System.out.println("Ingrese los elementos de la matriz " + nombre + ":");
        double[][] matriz = new double[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print("Ingrese el elemento en la posición [" + (i + 1) + "][" + (j + 1) + "]: ");
                matriz[i][j] = leerData.nextDouble();
            }
        }
        return matriz;
    }

    public static double[][] sumarMatrices(double[][] matrizA, double[][] matrizB) {
        int filas = matrizA.length;
        int columnas = matrizA[0].length;

        double[][] resultado = new double[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                resultado[i][j] = matrizA[i][j] + matrizB[i][j];
            }
        }

        return resultado;
    }

    public static void generarProblemaAvanzado() {
        Boolean[] problemaMostrado = {false, false};
        Random random = new Random();
        int[] datosLlenarA = new int[16];
        int[] datosLlenarB = new int[16];
        int numeroAleatorio = random.nextInt(3);
        System.out.println("Tema: Division de matrices");

        switch (numeroAleatorio) {
            case 0:
                if (!problemaMostrado[0]) {
                    double[][] matrizA = {
                        {5, 10, 1, 3},
                        {9, 14, 2, 6},
                        {7, 8, 15, 3},
                        {6, 8, 9, 2}
                    };

                    double[][] matrizB = {
                        {5, 13, 9, 4},
                        {1, 9, 6, 3},
                        {8, 11, 69, 33},
                        {25, 6, 7, 4}
                    };

                    System.out.println("Realice la division de la Matriz A/B");
                    System.out.println("Matriz A");
                    imprimirMatriz(matrizA);
                    System.out.println("Matriz B");
                    imprimirMatriz(matrizB);
                    double[][] resultadoDivision = divivisionMatrices(matrizA, matrizB);
                    double[][] respuestasUsuario = ingresarMatriz("Resultante", 4, 4);
                    if (sonMatricesIguales(resultadoDivision, respuestasUsuario)) {
                        System.out.println("¡Respuesta correcta!");
                        System.out.println("Haz logrado vencer la penalizacion, continua tú camino ");
                        System.out.println("La respuesta es:");
                        imprimirMatriz(resultadoDivision);
                    } else {
                        jugadorVivo = false;
                        System.out.println("FIN DEL JUEGO");
                        System.out.println("La respuesta correcta es: ");
                        imprimirMatriz(resultadoDivision);
                    }
                    break;
                }
            case 1:
                if (!problemaMostrado[1]) {
                    double[][] matrizA = {
                        {1, 12, 9, 8},
                        {7, 6, 3, 2},
                        {0, 5, 6, 14},
                        {6, 9, 6, 10}
                    };

                    double[][] matrizB = {
                        {8, 19, 20, 4},
                        {12, 33, 6, 8},
                        {4, 5, 9, 7},
                        {8, 22, 14, 6}
                    };

                    System.out.println("Realice la division de la Matriz A/B");
                    System.out.println("Matriz A");
                    imprimirMatriz(matrizA);
                    System.out.println("Matriz B");
                    imprimirMatriz(matrizB);
                    double[][] resultadoDivision = divivisionMatrices(matrizA, matrizB);
                    double[][] respuestasUsuario = ingresarMatriz("Resultante", 4, 4);
                    if (sonMatricesIguales(resultadoDivision, respuestasUsuario)) {
                        System.out.println("¡Respuesta correcta!");
                        System.out.println("Haz logrado vencer la penalizacion, continua tú camino ");
                        System.out.println("La respuesta es:");
                        imprimirMatriz(resultadoDivision);
                    } else {
                        jugadorVivo = false;
                        System.out.println("FIN DEL JUEGO");
                        System.out.println("La respuesta correcta es: ");
                        imprimirMatriz(resultadoDivision);
                    }
                    break;
                }

            default:
                System.out.println("Se llego el maximo de problemas resueltos");
                break;
        }

    }

    public static double[][] divivisionMatrices(double[][] matrizA, double[][] matrizB) {
        int filasA = matrizA.length;
        int columnasA = matrizA[0].length;
        int columnasB = matrizB[0].length;

        double[][] resultado = new double[filasA][columnasB];
        double determinanteB = calcularDeterminante(matrizB);
        if (determinanteB != 0) {
            double[][] matrizInversaB = calcularMatrizInversa(matrizB);

            resultado = multiplicarMatrices(matrizA, matrizInversaB);

        } else {
            System.out.println("El determinante de la matriz B es cero. No se puede realizar la división matricial.");
        }

        return resultado;
    }

    public static double calcularDeterminante(double[][] matriz) {
        int n = matriz.length;

        if (n != matriz[0].length) {
            throw new IllegalArgumentException("La matriz no es cuadrada");
        }

        if (n == 1) {
            return matriz[0][0];
        } else if (n == 2) {
            return matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0];
        } else {
            double determinante = 0;

            for (int i = 0; i < n; i++) {
                determinante += Math.pow(-1, i) * matriz[0][i] * calcularDeterminante(obtenerSubmatriz(matriz, 0, i));
            }

            return determinante;
        }
    }

    public static double[][] obtenerSubmatriz(double[][] matriz, int filaExcluir, int columnaExcluir) {
        int n = matriz.length;
        double[][] submatriz = new double[n - 1][n - 1];

        int subfila = 0;
        int subcolumna = 0;

        for (int i = 0; i < n; i++) {
            if (i != filaExcluir) {
                for (int j = 0; j < n; j++) {
                    if (j != columnaExcluir) {
                        submatriz[subfila][subcolumna] = matriz[i][j];
                        subcolumna++;
                    }
                }
                subfila++;
                subcolumna = 0;
            }
        }

        return submatriz;
    }

    public static boolean sonMatricesIguales(double[][] matrizA, double[][] matrizB) {
        int filas = matrizA.length;
        int columnas = matrizA[0].length;

        if (filas != matrizB.length || columnas != matrizB[0].length) {
            return false;  // Si las matrices tienen dimensiones diferentes, no son iguales
        }

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matrizA[i][j] != matrizB[i][j]) {
                    return false;  // Si al menos un elemento es diferente, las matrices no son iguales
                }
            }
        }

        return true;  // Si no se encontraron diferencias, las matrices son iguales
    }

    public static double[][] calcularMatrizInversa(double[][] matriz) {
        int n = matriz.length;

        // Crear una matriz aumentada [matriz | Identidad]
        double[][] matrizAumentada = new double[n][2 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrizAumentada[i][j] = matriz[i][j];
                matrizAumentada[i][j + n] = (i == j) ? 1 : 0;
            }
        }

        // Aplicar eliminación gaussiana para convertir la mitad izquierda en la identidad
        for (int i = 0; i < n; i++) {
            // Hacer el pivote igual a 1
            double pivote = matrizAumentada[i][i];
            for (int j = 0; j < 2 * n; j++) {
                matrizAumentada[i][j] /= pivote;
            }

            // Hacer otras filas cero en la columna actual
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = matrizAumentada[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        matrizAumentada[k][j] -= factor * matrizAumentada[i][j];
                    }
                }
            }
        }

        // Extraer la mitad derecha (matriz inversa)
        double[][] matrizInversa = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrizAumentada[i], n, matrizInversa[i], 0, n);
        }

        return matrizInversa;
    }

    public static double[][] multiplicarMatrices(double[][] matrizA, double[][] matrizB) {
        int filasA = matrizA.length;
        int columnasA = matrizA[0].length;
        int columnasB = matrizB[0].length;

        double[][] resultado = new double[filasA][columnasB];

        for (int i = 0; i < filasA; i++) {
            for (int j = 0; j < columnasB; j++) {
                for (int k = 0; k < columnasA; k++) {
                    resultado[i][j] += matrizA[i][k] * matrizB[k][j];
                }
                resultado[i][j]=Math.round(resultado[i][j]*1000.0)/1000.0;
            }
        }

        return resultado;
    }

    public static void imprimirMatriz(double[][] matriz) {
        for (double[] fila : matriz) {
            for (double elemento : fila) {
                System.out.print("|     "+elemento + "   |");
            }
            System.out.println();
        }
    }

}
