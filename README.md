### LiteFlow
Lightweight dataflow framework provides a simple model for building applications.

LiteFlowApi contains API for activities and flows creation. 
<br><br>

#### Goal.

Software development, maintenance and support should be easy and fast.
Principles should cover full software life cycle.
From initial concept, design and development, operation, maintenance and support, retirement and phase-out.

#### How?

1. Separation of concerns.

2. Reusable software components.

3. Business software developers should solve only business tasks.
   Framework should take care of everything else.
   
4. Components and Flow definition should be separated from runtime.

5. Components should be small, do only one thing and do it well.

6. Most of the components should be side-effect free and pure.
   (always produce the same results on the same input)

7. Components should be easily testable.

8. Framework should help to quickly see what is going on, find problem cause
   and help to fix it.


<br>

![ExampleFlow](/docs/images/ConvertAddNumbersFlow.png)
<br>
Very simple flow example.

<br>

##### Factorial calculation service demo

Calculating factorial can be time consuming.
Adding standard cache component to flow helps to save time and processing power. 

First time factorial will be calculated and stored in cache.
![CachedFactorialFlow](/docs/images/fact11a.gif)

Second time result value will be taken from cache.
![CachedFactorialFlow](/docs/images/fact11b.gif)

 