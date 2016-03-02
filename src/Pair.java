import java.io.Serializable;

public class Pair<K extends Comparable<K>,T extends Comparable<T>> implements Comparable<Pair<K,T>>,Serializable
{
	static final long serialVersionUID = 42L;
	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Pair pair = (Pair) o;

		if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
		if (second != null ? !second.equals(pair.second) : pair.second != null) return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = first != null ? first.hashCode() : 0;
		result = 31 * result + (second != null ? second.hashCode() : 0);
		return result;
	}

	public K first;
	public T second;
	public Pair(K first, T second)
	{
		this.first = first;
		this.second = second;
	}

	@Override
	public int compareTo(Pair<K,T> o)
	{
		if (first.equals(o.first)==false)
			return first.compareTo(o.first);
		return second.compareTo(o.second);
	}
}
