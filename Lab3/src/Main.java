import mpi.*;
import java.util.*;

public class Main {
    static final int LEVELS = 3;
    public static void main(String[] args) {
        // Задание 3.1
        
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int TAG = 0;

        int[] data = new int[1];
        int[] buf = {1, 3, 5};

        if (rank == 0) {
            data[0] = 2016;
            MPI.COMM_WORLD.Send(data, 0, 1, MPI.INT, 2, TAG);
        }
        else if (rank == 1) {
            MPI.COMM_WORLD.Send(buf, 0, buf.length, MPI.INT, 2, TAG);
        }
        else if (rank == 2) {
            Status st = MPI.COMM_WORLD.Probe(MPI.ANY_SOURCE, TAG);
            int count = st.Get_count(MPI.INT);
            int[] back_buf = new int[count];
            MPI.COMM_WORLD.Recv(back_buf, 0, count, MPI.INT, st.source, TAG);

            System.out.print("Rank = " + st.source + ": ");
            for (int i = 0; i < count; i++) {
                System.out.print(back_buf[i] + " ");
            }
            System.out.println();

            st = MPI.COMM_WORLD.Probe(MPI.ANY_SOURCE, TAG);
            count = st.Get_count(MPI.INT);
            int[] back_buf2 = new int[count];
            MPI.COMM_WORLD.Recv(back_buf2, 0, count, MPI.INT, st.source, TAG);

            System.out.print("Rank = " + st.source + ": ");
            for (int i = 0; i < count; i++) {
                System.out.print(back_buf2[i] + " ");
            }
            System.out.println();
        }

        MPI.Finalize();
    }
}