import java.time.*;
class offesteg
{
	public static void main(String gg[])
	{
		// OffsetDateTime dateTime = OffsetDateTime.of(2015, 10, 23, 12, 44, 43, 0, ZoneOffset.UTC);
		// OffsetDateTime dateTime = OffsetDateTime.now(ZoneOffset.UTC);
		Instant instant=Instant.now();
		OffsetDateTime dateTime = instant.atOffset( ZoneOffset.UTC );
		System.out.println(dateTime);
		OffsetDateTime dateTime2=dateTime;
		System.out.println(dateTime2);
	}
}