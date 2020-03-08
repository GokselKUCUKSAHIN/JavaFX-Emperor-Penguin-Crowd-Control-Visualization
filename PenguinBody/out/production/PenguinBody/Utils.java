public class Utils
{
    static double map(double x, double in_min, double in_max, double out_min, double out_max)
    {
        //double map
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

}
