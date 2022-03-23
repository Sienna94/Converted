


# :mask: MyCoronaV

> 코로나 검사 및 진료 가능한 병원 리스트를 제공하는 개인 프로젝트
>
> - 서울시에서 제공하는 확진자 정보 API를 사용하여 실시간 코로나 확진자 정보를 보여주는 개인 프로젝트였으나, 제공 API 중단으로 호흡기 진료 지정 의료기관 정보서비스 API로 교체
> - *MVVM* , *ViewModel*, *DataBinding* 등의 개발도구 연습을 목적으로 제작 

## 1. 제작 기간

- 2021.07 (2주)



## 2. 사용 기술

- ``Retrofit2``, `MVVM`, `ListView`, `GridView`, `ScrollView`, `ViewPager2` 



## 3. 핵심 기능 및 구조

### 3.1. 핵심기능

<img src="https://user-images.githubusercontent.com/69448123/154096395-0614d164-e0e0-421c-b770-b80b5104f1a1.png" alt="codit1-1" style="zoom:67%;" />

- 코로나 검사 및 진료 가능한 병원 리스트를 제공

- _Single Activity Multiple Fragment_구조

  - Activity : `MainActivity`
  - Fragment : `ViewPagerFragment` 에  `GridFragment` / `ListFragment` / `ScrollFragment`  을 넣어준 형태
    - ListFragment : RecyclerView
    - GridFragment : GridView
    - ScrollFragment : ScrollView에 item을 addView
    - 아이템레이아웃은 `item_row.xml`을 재사용

- `LiveData` 사용 UI 업데이트

  - `ListFragment`, `GridFragment`, `ScrollFragment` 이 동일한 데이터를 observe

  - 해당 ViewModel은 MainActivity에서 선언. 이를 위해 `activityViewModels()`를 활용

    - Activity  : 설명

      ```kotlin
      class MainActivity : AppCompatActivity(), ListFragment.ListRequestListener,
          GridFragment.ListRequestListener, ScrollFragment.ListRequestListener {
      ...
          //viewModel
          val viewModel: SharedViewModel by viewModels()
      
          override fun onCreate(savedInstanceState: Bundle?) {
      ...
              getCoronaList()
              viewModel.rows_live.run {
                  setFragments()		//viewModel
              }
          }
              
      ...
      
          private fun getCoronaList() {
              ...
              viewModel.getRows()
      		...
          }
      
      ```

    - Fragments  : 설명

      ```kotlin
      class ListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
      ...	
          private val model: SharedViewModel by activityViewModels()
      
      }
      ```

    

  - 각각의 프래그먼트에서 아이템 삭제시 실시간으로 모든 프래그먼트 UI에 반영 

    ```kotlin
    class SharedViewModel : ViewModel() {
        //live data
        var rows_live: MutableLiveData<ArrayList<Row>> = MutableLiveData<ArrayList<Row>>()
    ```

    - `MutableLiveData` 를 사용하여 아이템 삭제로 인한 데이터 수정이 가능하도록 함. 



### 3.2. 구조

- **MVVM(Model View View-Model)**

  <img src="https://user-images.githubusercontent.com/69448123/154233243-72a2305f-a486-4165-af8b-f540610eec5d.png" style="zoom:80%;" /> 





## 4. 트러블 슈팅

### 4.1.  Retrofit으로 XML 파싱 오류

> - API를 교체하는 과정에서 XML 컨버팅이 제대로 되지 않는 오류 발생
>
>   - `_Could not locate ResponseBody converter..._`
>
> - *Interceptor*를 통해 통신에 사용된 인증키가 잘못된 형태로 request되는 것을 확인
>
>   - 포털에서 제공되는 인증키 중 Decoding을 사용하여 해결 
>
>   - 이와 관련해 블로그 포스팅을 작성하였습니다.'
>
>     [CLICK!:point_right:](https://velog.io/@siennachang/Retrofit%EC%9C%BC%EB%A1%9C-XML-%ED%8C%8C%EC%8B%B1%ED%95%98%EA%B8%B0-%EC%82%BD%EC%A7%88)

### 4.3. 기타

- **splash 화면 구현**

  > 따로 ` SplashActivity`를 생성하지 않고 액티비티의 테마 배경을 변경하는 방식으로 구현하였습니다.
  >
  > **`themes.xml`**
  >
  > ```xml
  > <resources xmlns:tools="http://schemas.android.com/tools">
  >  ...
  >  <style name="SplashTheme" parent="Theme.AppCompat.NoActionBar">
  >      <item name="android:windowBackground">@drawable/splash</item>
  >  </style>
  > </resources>
  > ```
  >
  > 
  >
  > **`AndroidManifest.xml`**
  >
  > ```xml
  > <?xml version="1.0" encoding="utf-8"?>
  > <manifest xmlns:android="http://schemas.android.com/apk/res/android"
  >  package="com.example.mycoronav">
  > ...
  >      <activity android:name=".activity.MainActivity"
  >          android:theme="@style/SplashTheme">
  >          <intent-filter>
  >              <action android:name="android.intent.action.MAIN" />
  > 
  >              <category android:name="android.intent.category.LAUNCHER" />
  >          </intent-filter>
  >      </activity>
  >  </application>
  > 
  > </manifest>
  > ```
  >
  > 
  >
  > **`MainActivity.kt`**
  >
  > ```kotlin
  > class MainActivity : AppCompatActivity(), ListFragment.ListRequestListener,
  >  GridFragment.ListRequestListener, ScrollFragment.ListRequestListener {
  > ...
  > 
  >  override fun onCreate(savedInstanceState: Bundle?) {
  >      //after splash showed, set Theme default again
  >      setTheme(R.style.AppTheme)
  >      super.onCreate(savedInstanceState)
  >     ...
  >  }
  > ```
  >
  > - 참고 포스팅 :point_right:](https://lanace.github.io/articles/right-way-on-splash/)



## 5. 회고/ 느낀 점

- MVVM 패턴에 대한 잘못된 이해

  - Fragment와 Activity 간에서의 데이터 전달 문제를 해결하기 위한 수단으로 접근했고, 명확한 이해 없이 ViewModel과 LiveData를 사용했습니다. 

  - _View와 ViewModel의 역할분리_를 명확하게 구현하지 못한 점이 아쉽습니다.   

    :point_right: View가 ViewModel을 옵저빙하는 과정에서의 의존성을 줄이기 위해 _DataBinding_을 적용했습니다. **Mar6th**

    - 기존코드에서 adapter의 ViewHolder에서 view에 일일히 bind 해주었던 것을 xml에서 처리하도록 하여 보일러플레이트 코드를 줄였습니다.

      - 기존

        ```kotlin
        inner class ViewHolder(binding: ItemRowGridBinding) : RecyclerView.ViewHolder(binding.root){
            var id: TextView = binding.tvId
            var area: TextView = binding.tvArea
            var history: TextView = binding.tvContactHistory
            var date: TextView = binding.tvInfectedDate
            
            fun bind(rowItem: Row) {
                id.text = rowItem.corona19Id
                area.text = rowItem.corona19Area
                history.text = rowItem.corona19ContactHistory
                date.text = rowItem.corona19Date
                binding.delBtn.setOnClickListener { onClickDel?.invoke(rowItem)}
            }
        }
        ```

      - 변경

        ```kotlin
        inner class ViewHolder(binding: ItemRowGridBinding) : RecyclerView.ViewHolder(binding.root){
            
            fun bind(rowItem: Row) {
                binding.item = rowItem
                binding.delBtn.setOnClickListener { onClickDel?.invoke(rowItem)}
            }
        }
        ```

        ```xml
        <?xml version="1.0" encoding="utf-8"?>
        <layout xmlns:android="http://schemas.android.com/apk/res/android">
            <data>
                <variable
                    name="item"
                    type="com.example.mycoronav.vo.Row" />
            </data>
            <androidx.constraintlayout.widget.ConstraintLayout
                android:layout_width="match_parent"
                android:layout_height="170dp"
                xmlns:app="http://schemas.android.com/apk/res-auto">
        ...
                        <TextView
                            android:text="@{item.corona19Date}"/>
        ...
        ```

        

- **Android Jetpack** (Databinding,  LiveData, ViewModel) 학습 목적의 프로젝트로 다양한 기능은 구현하지 못했습니다.

  - 검색 기능, 지역별 카테고리 기능 등을 추가할 예정입니다

- Http통신시 Interceptor를 통한 로그 확인의 중요성을 느꼈습니다.

- API 교체로 RetrofitClient, RepositoryImpl, POJO class 등이 정리되지 않았습니다. 향후 사용하지 않는 코드를 정리할 예정입니다. 



