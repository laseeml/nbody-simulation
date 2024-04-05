public class Test {
    public static void main(String[] args) {
        // Step 1: parse command-line arguments
        double stoppingTime = Double.parseDouble(args[0]); // the duration of the simulation
        double dt = Double.parseDouble(args[1]); // the simulation time increment (Δt).
        final double G = 6.67430e-11;

        // Step 2: read universe from standard input
        int n = StdIn.readInt(); // the number of particles
        double radius = StdIn.readDouble();
        double[] px = new double[n];
        double[] py = new double[n]; // Positions
        double[] vx = new double[n];
        double[] vy = new double[n]; // Velocities
        double[] mass = new double[n];
        String[] image = new String[n];

        for (int i = 0; i < n; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            image[i] = StdIn.readString();
        }
        // Step 3. Initialize standard drawing.
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.enableDoubleBuffering();

        // Step 4. Play music on standard audio.
        StdAudio.playInBackground("2001.wav");

        // Step 5: simulate the universe (the big time loop)
        double[] fx = new double[n];
        double[] fy = new double[n];

        for (double t = 0.0; t < stoppingTime; t += dt) {
            for (int i = 0; i < n; i++) {
                // Step 5A. Calculate net forces.
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        double dx = px[j] - px[i];
                        double dy = py[j] - py[i];
                        double r = Math.sqrt(dx * dx + dy * dy);
                        double force = (G * mass[i] * mass[j]) / (r * r);
                        fx[i] += force * dx / r;
                        fy[i] += force * dy / r;
                    }
                }
            }
            // Step 5B. Update velocities and positions.
            for (int i = 0; i < n; i++) {
                vx[i] += dt * fx[i] / mass[i];
                vy[i] += dt * fy[i] / mass[i];
                px[i] += dt * vx[i];
                py[i] += dt * vy[i];
            }
            // Step 5C. Draw universe to standard drawing.
            StdDraw.clear();
            StdDraw.picture(0, 0, "starfield.jpg");
            for (int i = 0; i < n; i++) {
                StdDraw.picture(px[i], py[i], image[i]);
            }
            StdDraw.show();
            StdDraw.pause(20);
        }
        // Step 6. Print universe to standard output
        StdOut.printf("%d\n", n);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < n; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", px[i], py[i], vx[i], vy[i], mass[i], image[i]);
        }
    }
}
