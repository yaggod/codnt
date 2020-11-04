#include <iostream>
#include <math.h>
using namespace std;
int main()
{
	setlocale(0, "ru");
	cout << "Введите высшую степень уравнения: ";
	int step,num=0;
	cin >> step;
	float* ms = new float[step];
	for (int i = step; i >= 0; i--)
	{
		if (i == 1)
		{
			cout << "Введите коэффицент при х:   ";
		}
		else if (i == 0)
		{
			cout << "Введите коэффицент при свободном члене: ";
		}
		else
		{
			cout << "Введите коэффицент при х^" << i << ": ";
		}
		cin >> ms[num];
		num++;
	}
	num--;
	for (int i = 0; i <= step; i++)
	{
		if (ms[i] != 0)
		{
			if (ms[i] == 0)
			{
				continue;
			}
			if (num==1)
			{
				cout << ms[i] << "x";
			}
			else if (num == 0)
			{
				cout << ms[i];
			}
			else
			{
				cout << ms[i] << "x^" << num;
			}
			if ((ms[i + 1] > 0) and (i < step))
			{
				cout << "+";
			}
		}
		num--;
	}
	cout << "=0";
}