
class Fixed_Distribution_Peers
{
    int numberofseeds = 8;
}

public class P2P_Networking 
{
    public static void main(String[] args) {
        int numberofpeers = 5;
        int numberofseeds = 50;
        int max_size = 2097152; //2GB
        int max_speed_peers = 2048; //2048KBps
        int max_speed_seeds = 128; //128KBps

        int max_peers_per_seed = max_speed_peers / max_speed_seeds;
        System.out.println(max_peers_per_seed);

        int[] Process_Completion = new int[5];

        for (int i = 0; i < 5; i++) // Set all process completion to zero at the start
        {
            Process_Completion[i] = 0; 
        }

        Process_Completion[0] = 40;
        Process_Completion[1] = 20;
        Process_Completion[2] = 0;
        Process_Completion[3] = 60;
        Process_Completion[4] = 50;

        for (int i = 0; i<5; i++) {
            System.out.println("Process " + i + " is at " + Process_Completion[i] + "%");
        }



        

        



    }
}
