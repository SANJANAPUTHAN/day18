package Package;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class Consignment
{
	private String departure;
	private String destination;
	private LocalDate start_date;
	private LocalTime start_time;
	private int distance;
	private int travelspeed;
	private int travel_break;
	public Consignment() {
	}
	public Consignment(String departure, String destination,LocalDate start_date,LocalTime start_time, int distance,
			int travelspeed, int travel_break) {
		super();
		this.departure = departure;
		this.destination = destination;
		this.start_date = start_date;
		this.start_time = start_time;
		this.distance = distance;
		this.travelspeed = travelspeed;
		this.travel_break = travel_break;
	}

	
	public String getDeparture() {
		return departure;
	}


	public void setDeparture(String departure) {
		this.departure = departure;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public LocalDate getStart_date() {
		return start_date;
	}


	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
}


	public LocalTime getStart_time() {
		return start_time;
	}


	public void setStart_time(LocalTime start_time) {
		this.start_time = start_time;
	}


	public int getDistance() {
		return distance;
	}


	public void setDistance(int distance) {
		this.distance = distance;
	}


	public int getTravelspeed() {
		return travelspeed;
	}


	public void setTravelspeed(int travelspeed) {
		this.travelspeed = travelspeed;
	}


	public int getTravel_break() {
		return travel_break;
	}


	public void setTravel_break(int travel_break) {
		this.travel_break = travel_break;
	}


	public static void main(String[] args) 
	{
		String arrivalplace,departureplace,arrivaltime,arrivaldate;
		int distance,travelspeed,travelbreak,day;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter arrival destination: ");
		arrivalplace=sc.next();
		System.out.println("Enter departure destination: ");
		departureplace=sc.next();
		System.out.println("Enter arrival date: ");
		arrivaldate=sc.next();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		LocalDate date=LocalDate.parse(arrivaldate,formatter);
		System.out.println("Enter arrival time: ");
		arrivaltime=sc.next();
		System.out.println("Enter distance in kilometers: ");
		distance=sc.nextInt();
		System.out.println("Enter speed in hours: ");
		travelspeed=sc.nextInt();
		System.out.println("Enter breaktime in intervals of hours: ");
		travelbreak=sc.nextInt();
		LocalTime t=LocalTime.parse(arrivaltime);
		Consignment consignment=new Consignment(arrivalplace, departureplace,date, LocalTime.parse(arrivaltime), distance, travelspeed, travelbreak);
		identifyDepartureTime(consignment);
	}
	private static void identifyDepartureTime(Consignment consignment)
	{
		int day;
		if(checkSpecialconstraints(consignment.start_date))
		{
			consignment.start_date=setspecialconstraints(consignment.start_date);
			consignment.start_time=setConstraintTime(consignment.start_time);
		}
		else if((checkconstraint(consignment.start_date))!=0)
		{
			day=checkconstraint(consignment.start_date);
			consignment.start_date=setconstraintDate(consignment.start_date, day);
			consignment.start_time=setConstraintTime(consignment.start_time);
			
		}
		float time=getTime(consignment.distance,consignment.travelspeed);
		if(consignment.travel_break!=0)
		{
			time=getbreak(time,consignment.travel_break);
		}
		float minutes=time%1;
		minutes=(float)(time-Math.floor(time));
		minutes=minutes*100;
		ArrayList<String> datetime=getdatetime(consignment.start_time,time,consignment.start_date);
		LocalTime nextTime=LocalTime.parse(datetime.get(1));
		nextTime=nextTime.plus((long) minutes,ChronoUnit.MINUTES);
		LocalDate d=LocalDate.parse(datetime.get(0));
		System.out.print("The parcel will reach at ");
		System.out.println(nextTime+"(approximately)  "+d+" "+d.getDayOfWeek());
	}
	private static LocalDate setspecialconstraints(LocalDate start_date) 
	{
		
			int day=1;
			start_date=setconstraintDate(start_date, day);
			if(checkconstraint(start_date)!=0)
			{
				day=checkconstraint(start_date);
				start_date=setconstraintDate(start_date, day);				
			}
			
		
		return start_date;
	}

	private static boolean checkSpecialconstraints(LocalDate start_date)
	{
		if(start_date.isEqual(LocalDate.of(start_date.getYear(), 1, 1))||start_date.isEqual(LocalDate.of(start_date.getYear(), 8, 15))||
				start_date.isEqual(LocalDate.of(start_date.getYear(), 10, 2))||start_date.isEqual(LocalDate.of(start_date.getYear(), 1, 26)))
		{
			return true;
		}
		return false;
	}
	private static LocalTime setConstraintTime(LocalTime time)
	{
		return LocalTime.parse("06:00:00");
	}
	private static int checkconstraint(LocalDate start_date) {
		if(start_date.getDayOfWeek()==DayOfWeek.SUNDAY)
		{
			return 1;
		}
		else if(start_date.getDayOfWeek()==DayOfWeek.SATURDAY)
		{
			return 2;
		}
		return 0;
	}

	private static LocalDate setconstraintDate(LocalDate date,int day)
	{
		date=date.plus(day,ChronoUnit.DAYS);
		return date;
		
	}
	private static ArrayList<String> getdatetime(LocalTime start_time, float time, LocalDate start_date) {

		int hours=start_time.getHour();
		ArrayList<String> datetime=new ArrayList<String>();
		if(hours<12 && time<24)
		{
			start_time=getTime(start_time,time);
			datetime.add(String.valueOf(start_date));
			datetime.add(String.valueOf(start_time));
		}
		else if((hours<12 || hours>12) && time>24)
		{
			while(time>24)
			{
				start_date=start_date.plus(1,ChronoUnit.DAYS);
				if(checkSpecialconstraints(start_date))
				{
					start_date=setspecialconstraints(start_date);
					start_time=setConstraintTime(start_time);
				}
				else if((checkconstraint(start_date))!=0)
				{
					int day=checkconstraint(start_date);
					start_date=setconstraintDate(start_date, day);
					time=time+(start_time.getHour()-6);
					start_time=setConstraintTime(start_time);
					if(time<24)
					{
						break;
					}
					
				}
				start_time=start_time.plus(24,ChronoUnit.HOURS);
				time=time-24;
			}
			if(24-hours < time)
			{
				start_date=start_date.plus(1,ChronoUnit.DAYS);
				
			}
			start_time=getTime(start_time, time);
			datetime.add(String.valueOf(start_date));
			datetime.add(String.valueOf(start_time));
		}
		else if(hours>12 && time<24)
		{
			if(24-hours < time)
			{
				start_date=start_date.plus(1,ChronoUnit.DAYS);
				if(checkSpecialconstraints(start_date))
				{
					start_date=setspecialconstraints(start_date);
					start_time=setConstraintTime(start_time);
				}
				else if((checkconstraint(start_date))!=0)
				{
					int day=checkconstraint(start_date);
					time=time-(24-hours);
					start_date=setconstraintDate(start_date, day);
					start_time=setConstraintTime(start_time);
					
					
				}
				
			}
			start_time=getTime(start_time, time);
			datetime.add(String.valueOf(start_date));
			datetime.add(String.valueOf(start_time));
		}
		
		return datetime;
	}


	

	private static LocalTime getTime(LocalTime start_time,float time) {
		start_time=start_time.plus((long)time,ChronoUnit.HOURS);
		return start_time;
	}


	private static float getbreak(float time, int breaktime) {
		if(breaktime<time)
		{
			int cal=(int)time/breaktime;
			time+=cal;
		}
		return time;
	}


	private static float getTime(int distance, int travelspeed)
	{
		return (float)distance/(float)travelspeed;
	}
}
