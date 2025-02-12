import java.util.Scanner;

class Fixed_Distribution_Peers
{
    int Find_Number_Of_Seeds(int numberofseeds)
    {
        return numberofseeds/4;
    }

    int Number_Of_Incompleted_Peers(int[] Process_Completion, int numberofpeers)
    {
        int count = 0;
        for (int i = 0; i < numberofpeers; i++)
        {
            if (Process_Completion[i] != 100)
            {
                count++;
            }
        }
        return count;
    }

    int CalculatingTimeTaken(int[] Total_Time_Taken,int numberofseeds_per_peer, int max_size, int max_speed_peers, int Percentage_Completed,int i)
    {
        int Time_Taken = 0;
        int done = Percentage_Completed * max_size / 100;
        Time_Taken = done / (max_speed_peers*numberofseeds_per_peer);
        Total_Time_Taken[i] += Time_Taken;
        return 0;
    }

    int Avg_Time(int[] Process_Completion,int numberofpeers, int numberofseeds, int max_size, int max_speed_peers, int max_speed_seeds,int max_seeds_per_peer)
    {
        int[] Total_Time_Taken = new int[numberofpeers];

        for(int i=0;i<numberofpeers;i++)
        {
            Total_Time_Taken[i] = 0;
        }

        
        int numberofseeds_per_peer = Find_Number_Of_Seeds(numberofseeds);
        if(numberofseeds_per_peer>max_seeds_per_peer)
        {
            numberofseeds_per_peer = max_seeds_per_peer;
        }
        //System.out.println("Number of seeds per peer: " + numberofseeds_per_peer);
        
        
        
        if (numberofpeers<=4 || Number_Of_Incompleted_Peers(Process_Completion, numberofpeers)<=4)
        {
            for(int i=0;i<numberofpeers;i++)
            {
                int Process_Complete_Percentage = Process_Completion[i];
                CalculatingTimeTaken(Total_Time_Taken,numberofseeds_per_peer,max_size,max_speed_peers,Process_Complete_Percentage,i);
                int Process_Complete_Percentage_Left = 100 - Process_Complete_Percentage;
                CalculatingTimeTaken(Total_Time_Taken, numberofseeds_per_peer, max_size, max_speed_peers, Process_Complete_Percentage_Left, i);
            } 
        }

        else
        {
            
        }

        int Avg_Time = 0;
        for(int i =0;i<numberofpeers;i++)
        {
            Avg_Time += Total_Time_Taken[i];
        }

        return Avg_Time/numberofpeers;
    }
}

public class P2P_Networking 
{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of peers: ");
        int numberofpeers = sc.nextInt();                        // Number of peers=5
        System.out.println("Enter the number of seeds: ");
        int numberofseeds = sc.nextInt();                        // Number of seeds=50
        int max_size = 2097152;                                  //2GB
        int max_speed_peers = 2048;                              //2048KBps
        int max_speed_seeds = 128;                               //128KBps

        int max_seeds_per_peer = max_speed_peers / max_speed_seeds;
        //System.out.println(max_seeds_per_peer);

        int[] Process_Completion = new int[numberofpeers];

        for (int i = 0; i < numberofpeers; i++) // Set all process completion to zero at the start
        {
            Process_Completion[i] = 0; 
        }

        Process_Completion[0] = 40;
        Process_Completion[1] = 20;
        Process_Completion[2] = 0;
        Process_Completion[3] = 60;
        Process_Completion[4] = 50;

        for (int i = 0; i<numberofpeers; i++) {
            System.out.println("Process " + i + " is at " + Process_Completion[i] + "%");
        }



       Fixed_Distribution_Peers obj = new Fixed_Distribution_Peers();
         obj.Avg_Time(Process_Completion,numberofpeers, numberofseeds, max_size, max_speed_peers, max_speed_seeds,max_seeds_per_peer); 

        



    }
}
