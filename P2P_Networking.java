import java.util.Scanner;

class Fixed_Distribution_Peers
{
    int Find_Number_Of_Seeds(int numberofseeds)
    {
        return numberofseeds/4;
    }

    int Number_Of_Incompleted_Peers(float[] Process_Completion, int numberofpeers)
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

    int Find_Max_Process_Completion(float[] Process_Completion, int numberofpeers)
    {
        float max = 0;
        int index = 0;
        for (int i = 0; i < numberofpeers; i++)
        {
            if (Process_Completion[i] > max && Process_Completion[i] != 100)
            {
                max = Process_Completion[i];
                index = i;
            }
        }
        //Process_Completion[index] = 100;
        return index;
    }

    int SortArray(float[] Process_Completion, int numberofpeers)
    {
        for(int i=0;i<numberofpeers;i++)
        {
            for(int j=0;j<numberofpeers-i-1;j++)
            {
                if(Process_Completion[j]<Process_Completion[j+1])
                {
                    float temp = Process_Completion[j];
                    Process_Completion[j] = Process_Completion[j+1];
                    Process_Completion[j+1] = temp;
                }
            }
        }
        return 0;
    }

    int Add_Process_Completion_To_Other_Three_Peers(float[] Process_Completion, int numberofpeers, float Process_Complete_Percentage, int index)
    {
        if(index+3<=numberofpeers-1)
        {
            Process_Completion[index+1] += Process_Complete_Percentage;
            Process_Completion[index+2] += Process_Complete_Percentage;
            Process_Completion[index+3] += Process_Complete_Percentage;
        }
        else
        {
            if(index+2<=numberofpeers-1)
            {
                Process_Completion[index+1] += Process_Complete_Percentage;
                Process_Completion[index+2] += Process_Complete_Percentage;
            }
            else if(index+1<=numberofpeers-1)
            {
                Process_Completion[index+1] += Process_Complete_Percentage;
            }
        }
        return 0;
    }

    int Add_Time_Taken_To_Other_All_Peers(float[] Total_Time_Taken, int numberofpeers, float Time_Taken, int index)
    {
        for(int i=index+1;i<numberofpeers;i++)
        {
            Total_Time_Taken[i] += Time_Taken;
        }
        return 0;
    }

    int PrintAllArray(float[] Array,int numberofpeers)
    {
        for(int i=0;i<numberofpeers;i++)
        {
            System.out.print(Array[i] + " ");
        }
        System.out.println();
        return 0;
    }

    float CalculatingTimeTaken(float[] Total_Time_Taken,int numberofseeds_per_peer, int max_size, int max_speed_seeds, float Percentage_Completed,int i)
    {
        System.out.println("Max Peer Speed " + max_speed_seeds);
        float Time_Taken = 0;
        float done = ((float)Percentage_Completed/100) * max_size;
        System.out.println("Done: " + done);
        Time_Taken = done / (max_speed_seeds*numberofseeds_per_peer);
        Total_Time_Taken[i] += Time_Taken;
        return Time_Taken;
    }

    float Avg_Time(float[] Process_Completion,int numberofpeers, int numberofseeds, int max_size, int max_speed_peers, int max_speed_seeds,int max_seeds_per_peer)
    {
        float[] Total_Time_Taken = new float[numberofpeers];

        for(int i=0;i<numberofpeers;i++)
        {
            Total_Time_Taken[i] = 0;
        }

        
        int numberofseeds_per_peer = Find_Number_Of_Seeds(numberofseeds);
        if(numberofseeds_per_peer>max_seeds_per_peer)
        {
            numberofseeds_per_peer = max_seeds_per_peer;
        }
        System.out.println("Number of seeds per peer: " + numberofseeds_per_peer);
        
        
        
        if (numberofpeers<=4 || Number_Of_Incompleted_Peers(Process_Completion, numberofpeers)<=4)
        {
            for(int i=0;i<numberofpeers;i++)
            {
                float Process_Complete_Percentage = Process_Completion[i];
                CalculatingTimeTaken(Total_Time_Taken,numberofseeds_per_peer,max_size,max_speed_seeds,Process_Complete_Percentage,i);
                float Process_Complete_Percentage_Left = 100 - Process_Complete_Percentage;
                CalculatingTimeTaken(Total_Time_Taken, numberofseeds_per_peer, max_size, max_speed_seeds, Process_Complete_Percentage_Left, i);
            } 
        }

        else
        {
            SortArray(Process_Completion, numberofpeers);
            
            for (int i=0; i<numberofpeers; i++) 
            {
                float Process_Complete_Percentage = Process_Completion[i];
                float Time_Taken = CalculatingTimeTaken(Total_Time_Taken, numberofseeds_per_peer, max_size, max_speed_seeds, Process_Complete_Percentage, i);
                System.out.println("Time Taken: " + Time_Taken);
            }

            while(Number_Of_Incompleted_Peers(Process_Completion, numberofpeers)>0)
            {
                PrintAllArray(Process_Completion, numberofpeers);
                PrintAllArray(Total_Time_Taken, numberofpeers);
                int index = Find_Max_Process_Completion(Process_Completion, numberofpeers);
                System.out.println("Index="+index);
                float Process_Complete_Percentage = Process_Completion[index];
                System.out.println("Process Complete Percentage: " + Process_Complete_Percentage);
    
                float Process_Complete_Percentage_Left = 100 - Process_Complete_Percentage;
                float Time_Taken = CalculatingTimeTaken(Total_Time_Taken, numberofseeds_per_peer, max_size, max_speed_seeds, Process_Complete_Percentage_Left, index);
                System.out.println("Time Taken: " + Time_Taken);
                Add_Process_Completion_To_Other_Three_Peers(Process_Completion, numberofpeers, Process_Complete_Percentage_Left, index);
                Add_Time_Taken_To_Other_All_Peers(Total_Time_Taken, numberofpeers, Time_Taken, index);
                Process_Completion[index] = 100;

                PrintAllArray(Process_Completion, numberofpeers);
                PrintAllArray(Total_Time_Taken, numberofpeers);
                System.out.println("-------------------------------------------------");

            }
        }

        float Avg_Time = 0;
        for(int i =0;i<numberofpeers;i++)
        {
            Avg_Time += Total_Time_Taken[i];
        }

        return Avg_Time/numberofpeers;
    }
}

class Equal_Distribution_Peers
{

    int Number_Of_Incompleted_Peers(float[] Process_Completion, int numberofpeers)
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

    int Find_Max_Process_Completion(float[] Process_Completion, int numberofpeers,int Count)
    {
        float max = 0;
        int index = 0;
        for (int i=Count; i < numberofpeers; i++)
        {
            if (Process_Completion[i] > max && Process_Completion[i] != 100)
            {
                max = Process_Completion[i];
                index = i;
            }
        }
        if(index>=Count)
        {
            return index;
        }
        //Process_Completion[index] = 100;
        else
        {
            return -1;
        }
    }

    int SortArray(float[] Process_Completion, int numberofpeers)
    {
        for(int i=0;i<numberofpeers;i++)
        {
            for(int j=0;j<numberofpeers-i-1;j++)
            {
                if(Process_Completion[j]<Process_Completion[j+1])
                {
                    float temp = Process_Completion[j];
                    Process_Completion[j] = Process_Completion[j+1];
                    Process_Completion[j+1] = temp;
                }
            }
        }
        return 0;
    }

    float CalculatingTimeTaken(float[] Total_Time_Taken,int numberofseeds_per_peer, int max_size, int max_speed_seeds, float Percentage_Completed,int i)
    {
        //System.out.println("Max Peer Speed " + max_speed_seeds);
        float Time_Taken = 0;
        float done = ((float)Percentage_Completed/100) * max_size;
        //System.out.println("Done: " + done);
        Time_Taken = done / (max_speed_seeds*numberofseeds_per_peer);
        Total_Time_Taken[i] += Time_Taken;
        return Time_Taken;
    }


    boolean Check_If_All_Peers_Entered(int[] Process_Entered, int numberofpeers)
    {
        for(int i=0;i<numberofpeers-1;i++)
        {
            if(Process_Entered[i]==0)
            {
                return true;
            }
        }
        return false;
    }

    int Count_Number_Of_Ones(int[] Process_Entered, int numberofpeers)
    {
        int count = 0;
        for(int i=0;i<numberofpeers;i++)
        {
            if(Process_Entered[i]==1)
            {
                count++;
            }
        }
        return count;
    }

    float Avg_Time(float[] Process_Completion,int numberofpeers, int numberofseeds, int max_size, int max_speed_peers, int max_speed_seeds,int max_seeds_per_peer)
    {
        float[] Total_Time_Taken = new float[numberofpeers];
        for(int i=0;i<numberofpeers;i++)
        {
            Total_Time_Taken[i] = 0;
        }

        float[] Temp_Process_Completion = new float[numberofpeers];
        for(int i=0;i<numberofpeers;i++)
        {
            Temp_Process_Completion[i] = 0;
        }

        int[] Process_Entered = new int[numberofpeers];
        for(int i=0;i<numberofpeers;i++)
        {
            Process_Entered[i] = 0;
        }

        SortArray(Process_Completion, numberofpeers);
        System.out.println("Sorted Array: ");
        for(int i=0;i<numberofpeers;i++)
        {
            System.out.print(Process_Completion[i] + " ");
        }
        int Count=0;
        System.out.println("-------------------------------------------------");
        while(Check_If_All_Peers_Entered(Process_Entered, numberofpeers))
        {
            System.out.println("Count: " + Count);
            System.out.println(Process_Completion[Count]);
            int Index = Find_Max_Process_Completion(Process_Completion, numberofpeers,Count);
            float Max_Process_Completion = Process_Completion[Index];
            Process_Entered[Index] = 1;
            int Next_Index = Index+1;
            //Process_Entered[Next_Index] = 1;
            float Max_Process_Completion_2 = Process_Completion[Next_Index];
            float Temp_Max_Process_Completion_2 = Max_Process_Completion_2;
            while(Max_Process_Completion==Max_Process_Completion_2)
            {
                Process_Entered[Next_Index] = 1;
                Next_Index++;
                Max_Process_Completion_2 = Process_Completion[Next_Index];
                Count++;
            }
            System.out.println("Max Process Completion: " + Max_Process_Completion);
            System.out.println("Max Process Completion 2: " + Max_Process_Completion_2);
            float Temp_Process_Completed_1 = Max_Process_Completion - Max_Process_Completion_2;
            Temp_Process_Completion[Index] += Max_Process_Completion - Max_Process_Completion_2;
            
            int Count_Number_Of_Ones = Count_Number_Of_Ones(Process_Entered, numberofpeers);
            int Temp_Number_Of_Seed_Per_Peer = numberofseeds/Count_Number_Of_Ones;
            if(Temp_Number_Of_Seed_Per_Peer>max_seeds_per_peer)
            {
                Temp_Number_Of_Seed_Per_Peer = max_seeds_per_peer;
            }

            


            float Time_Taken = CalculatingTimeTaken(Total_Time_Taken, Temp_Number_Of_Seed_Per_Peer, max_size, max_speed_seeds, Temp_Process_Completed_1, Index);
            System.out.println("Count"+Count);
            System.out.println("Index"+Index);
            for(int i=Index;i<Count;i++)
            {
                Temp_Process_Completion[i+1] += Temp_Process_Completed_1;
                Total_Time_Taken[i+1] += Time_Taken;
            }

            System.out.println("Time Taken: " + Time_Taken);
            for(int i=0;i<Index;i++)
            {
                Total_Time_Taken[i] += Time_Taken;
            }
            for(int i=0;i<Index;i++)
            {
                Temp_Process_Completion[i] += Temp_Process_Completed_1;
            }
            

            System.out.println("Array:" );
            for(int i=0;i<numberofpeers;i++)
            {
                System.out.print(Temp_Process_Completion[i] + " ");
            }

            System.out.println("Array:" );
            for(int i=0;i<numberofpeers;i++)
            {
                System.out.print(Process_Completion[i] + " ");
            }

            System.out.println("Array:" );
            for(int i=0;i<numberofpeers;i++)
            {
                System.out.print(Process_Entered[i] + " ");
            }

            Count++;

            System.out.println();
            for(int i=0;i<numberofpeers;i++)
            {
                System.out.println(Total_Time_Taken[i] + "  ");
            }
            System.out.println();
            
            System.out.println("Count: " + Count);
            System.out.println("-------------------------------------------------");
            
        }
        System.out.println();
        for(int i=0;i<numberofpeers;i++)
        {
            System.out.print(Process_Completion[i] + " ");
        }
        System.out.println();
        for(int i=0;i<numberofpeers;i++)
        {
            System.out.print(Total_Time_Taken[i] + " ");
        }
        System.out.println();

        System.out.println("-------------------------------------------------");

        while(Number_Of_Incompleted_Peers(Process_Completion, numberofpeers)>0)
        {
            int count_incomplete_peers = Number_Of_Incompleted_Peers(Process_Completion, numberofpeers);
            int Temp_Number_Of_Seed_Per_Peer = numberofseeds/count_incomplete_peers;
            if(Temp_Number_Of_Seed_Per_Peer>max_seeds_per_peer)
            {
                Temp_Number_Of_Seed_Per_Peer = max_seeds_per_peer;
            }
            int index = Find_Max_Process_Completion(Process_Completion, numberofpeers, 0);
            float Process_Complete_Percentage = Process_Completion[index];
            float Process_Complete_Percentage_Left = 100 - Process_Complete_Percentage;
            float Time_Taken = CalculatingTimeTaken(Total_Time_Taken, Temp_Number_Of_Seed_Per_Peer, max_size, max_speed_seeds, Process_Complete_Percentage_Left, index);
            for(int i=index;i<numberofpeers;i++)
            {
                Process_Completion[i] += Process_Complete_Percentage_Left;
            }
            for(int i=index+1;i<numberofpeers;i++)
            {
                Total_Time_Taken[i] += Time_Taken;            
            }
            System.out.println("Time Taken: " + Time_Taken);
            System.out.println();
            for(int i=0;i<numberofpeers;i++)
            {
                System.out.print(Process_Completion[i] + " ");
            }
            System.out.println();

            System.out.println();
            for(int i=0;i<numberofpeers;i++)
            {
                System.out.print(Total_Time_Taken[i] + " ");
            }
            System.out.println();
            System.out.println("-------------------------------------------------");
        }

        









        float Avg_Time = 0;
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

        float[] Process_Completion = new float[numberofpeers];

        for (int i = 0; i < numberofpeers; i++) // Set all process completion to zero at the start
        {
            Process_Completion[i] = 0; 
        }

        Process_Completion[0] = 60;
        Process_Completion[1] = 20;
        Process_Completion[2] = 0;
        Process_Completion[3] = 60;
        Process_Completion[4] = 40;

        for (int i = 0; i<numberofpeers; i++) {
            System.out.println("Process " + i + " is at " + Process_Completion[i] + "%");
        }



    //    Fixed_Distribution_Peers obj = new Fixed_Distribution_Peers();
    //    float Avg_Time = obj.Avg_Time(Process_Completion,numberofpeers, numberofseeds, max_size, max_speed_peers, max_speed_seeds,max_seeds_per_peer);
    //    System.out.println("Average time taken by all peers to download the file: " + Avg_Time + " seconds");

        Equal_Distribution_Peers obj = new Equal_Distribution_Peers();
        float Avg_Time = obj.Avg_Time(Process_Completion,numberofpeers, numberofseeds, max_size, max_speed_peers, max_speed_seeds,max_seeds_per_peer);
        System.out.println("Average time taken by all peers to download the file: " + Avg_Time + " seconds");

        



    }
}
