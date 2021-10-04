package com.example

import com.example.datasource.CovidRemoteDataSource
import com.example.remote.data.CovidRemoteDataSourceImpl
import com.example.remote.data.CovidWS
import com.example.remote.data.dto.*
import com.example.repository.CovidRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CovidRepositoryTest {

    var api: CovidWS = mockk()
    private var remoteDataSource: CovidRemoteDataSource = CovidRemoteDataSourceImpl(api)
    private val repository = CovidRepositoryImpl(remoteDataSource)

    private val country1Date1 = PlaceDataDto(
        COUNTRY1__ID,
        COUNTRY1__NAME,
        COUNTRY1_DATE1,
        COUNTRY1_DATE1__TODAY_CONFIRMED,
        COUNTRY1_DATE1__TODAY_DEATHS,
        COUNTRY1_DATE1__TODAY_NEW_CONFIRMED,
        COUNTRY1_DATE1__TODAY_NEW_DEATHS,
        COUNTRY1_DATE1__TODAY_NEW_OPEN_CASES,
        COUNTRY1_DATE1__TODAY_NEW_RECOVERED,
        emptyList()
    )
    private val country1Date2 = PlaceDataDto(
        COUNTRY1__ID,
        COUNTRY1__NAME,
        COUNTRY1_DATE2,
        COUNTRY1_DATE2__TODAY_CONFIRMED,
        COUNTRY1_DATE2__TODAY_DEATHS,
        COUNTRY1_DATE2__TODAY_NEW_CONFIRMED,
        COUNTRY1_DATE2__TODAY_NEW_DEATHS,
        COUNTRY1_DATE2__TODAY_NEW_OPEN_CASES,
        COUNTRY1_DATE2__TODAY_NEW_RECOVERED,
        emptyList()
    )

    private val country2Date1 = PlaceDataDto(
        COUNTRY2__ID,
        COUNTRY2__NAME,
        COUNTRY2_DATE1,
        COUNTRY2_DATE1__TODAY_CONFIRMED,
        COUNTRY2_DATE1__TODAY_DEATHS,
        COUNTRY2_DATE1__TODAY_NEW_CONFIRMED,
        COUNTRY2_DATE1__TODAY_NEW_DEATHS,
        COUNTRY2_DATE1__TODAY_NEW_OPEN_CASES,
        COUNTRY2_DATE1__TODAY_NEW_RECOVERED,
        emptyList()
    )
    private val country2Date2 = PlaceDataDto(
        COUNTRY2__ID,
        COUNTRY2__NAME,
        COUNTRY2_DATE2,
        COUNTRY2_DATE2__TODAY_CONFIRMED,
        COUNTRY2_DATE2__TODAY_DEATHS,
        COUNTRY2_DATE2__TODAY_NEW_CONFIRMED,
        COUNTRY2_DATE2__TODAY_NEW_DEATHS,
        COUNTRY2_DATE2__TODAY_NEW_OPEN_CASES,
        COUNTRY2_DATE2__TODAY_NEW_RECOVERED,
        emptyList()
    )

    private val spainDate1 = PlaceDataDto(
        SPAIN__ID,
        SPAIN__NAME,
        SPAIN_DATE1,
        SPAIN_DATE1__TODAY_CONFIRMED,
        SPAIN_DATE1__TODAY_DEATHS,
        SPAIN_DATE1__TODAY_NEW_CONFIRMED,
        SPAIN_DATE1__TODAY_NEW_DEATHS,
        SPAIN_DATE1__TODAY_NEW_OPEN_CASES,
        SPAIN_DATE1__TODAY_NEW_RECOVERED,
        emptyList()
    )
    private val spainDate2 = PlaceDataDto(
        SPAIN__ID,
        SPAIN__NAME,
        SPAIN_DATE2,
        SPAIN_DATE2__TODAY_CONFIRMED,
        SPAIN_DATE2__TODAY_DEATHS,
        SPAIN_DATE2__TODAY_NEW_CONFIRMED,
        SPAIN_DATE2__TODAY_NEW_DEATHS,
        SPAIN_DATE2__TODAY_NEW_OPEN_CASES,
        SPAIN_DATE2__TODAY_NEW_RECOVERED,
        emptyList()
    )
    private val total = CovidTotalDataDto(TO_DATE, TOTAL_NAME, TOTAL_CONFIRMED, TOTAL_DEATHS)
    private val infoDate1 = InfoDataDto(FROM_DATE)
    private val infoDate2 = InfoDataDto(TO_DATE)

    @Test
    fun testGetWorldData() {
        runBlocking {
            val countriesDate1 = mutableMapOf<String, PlaceDataDto>()
            countriesDate1[COUNTRY1__ID] = country1Date1
            countriesDate1[COUNTRY2__ID] = country2Date1
            countriesDate1[SPAIN__ID] = spainDate1
            val date1 = DateDataDto(countriesDate1, infoDate1)

            val countriesDate2 = mutableMapOf<String, PlaceDataDto>()
            countriesDate2[COUNTRY1__ID] = country1Date2
            countriesDate2[COUNTRY2__ID] = country2Date2
            countriesDate2[SPAIN__ID] = spainDate2
            val date2 = DateDataDto(countriesDate2, infoDate2)

            val dates = mutableMapOf<String, DateDataDto>()
            dates[FROM_DATE] = date1
            dates[TO_DATE] = date2

            coEvery { api.getData(FROM_DATE, TO_DATE) } returns DataResponseDto(dates, total)

            DateUtils.getApiDateFormatted(FROM_DATE)?.let {
                val result = repository.getWorldDataByDateRange(
                    it,
                    DateUtils.getApiDateFormatted(TO_DATE),
                )

                val country1Bo = result.placesData.firstOrNull { placeData ->  placeData.id == COUNTRY1__ID }
                val country2Bo = result.placesData.firstOrNull { placeData ->  placeData.id == COUNTRY2__ID }
                val spainBo = result.placesData.firstOrNull { placeData ->  placeData.id == SPAIN__ID }

                assert(result.total?.confirmed == TOTAL_CONFIRMED)
                assert(result.total?.deaths == TOTAL_DEATHS)

                assert(country1Bo?.id == COUNTRY1__ID)
                assert(country1Bo?.name == COUNTRY1__NAME)
                assert(country1Bo?.confirmed == COUNTRY1_DATE2__TODAY_CONFIRMED)
                assert(country1Bo?.deaths == COUNTRY1_DATE2__TODAY_DEATHS)
                assert(country1Bo?.newConfirmed == COUNTRY1_DATE2__TODAY_NEW_CONFIRMED + COUNTRY1_DATE1__TODAY_NEW_CONFIRMED)
                assert(country1Bo?.newDeaths == COUNTRY1_DATE2__TODAY_NEW_DEATHS + COUNTRY1_DATE1__TODAY_NEW_DEATHS)
                assert(country1Bo?.newOpenCases == COUNTRY1_DATE2__TODAY_NEW_OPEN_CASES + COUNTRY1_DATE1__TODAY_NEW_OPEN_CASES)
                assert(country1Bo?.newRecovered == COUNTRY1_DATE2__TODAY_NEW_RECOVERED + COUNTRY1_DATE1__TODAY_NEW_RECOVERED)

                assert(country2Bo?.id == COUNTRY2__ID)
                assert(country2Bo?.name == COUNTRY2__NAME)
                assert(country2Bo?.confirmed == COUNTRY2_DATE2__TODAY_CONFIRMED)
                assert(country2Bo?.deaths == COUNTRY2_DATE2__TODAY_DEATHS)
                assert(country2Bo?.newConfirmed == COUNTRY2_DATE2__TODAY_NEW_CONFIRMED + COUNTRY2_DATE1__TODAY_NEW_CONFIRMED)
                assert(country2Bo?.newDeaths == COUNTRY2_DATE2__TODAY_NEW_DEATHS + COUNTRY2_DATE1__TODAY_NEW_DEATHS)
                assert(country2Bo?.newOpenCases == COUNTRY2_DATE2__TODAY_NEW_OPEN_CASES + COUNTRY2_DATE1__TODAY_NEW_OPEN_CASES)
                assert(country2Bo?.newRecovered == COUNTRY2_DATE2__TODAY_NEW_RECOVERED + COUNTRY2_DATE1__TODAY_NEW_RECOVERED)

                assert(spainBo?.id == SPAIN__ID)
                assert(spainBo?.name == SPAIN__NAME)
                assert(spainBo?.confirmed == SPAIN_DATE2__TODAY_CONFIRMED)
                assert(spainBo?.deaths == SPAIN_DATE2__TODAY_DEATHS)
                assert(spainBo?.newConfirmed == SPAIN_DATE2__TODAY_NEW_CONFIRMED + SPAIN_DATE1__TODAY_NEW_CONFIRMED)
                assert(spainBo?.newDeaths == SPAIN_DATE2__TODAY_NEW_DEATHS + SPAIN_DATE1__TODAY_NEW_DEATHS)
                assert(spainBo?.newOpenCases == SPAIN_DATE2__TODAY_NEW_OPEN_CASES + SPAIN_DATE1__TODAY_NEW_OPEN_CASES)
                assert(spainBo?.newRecovered == SPAIN_DATE2__TODAY_NEW_RECOVERED + SPAIN_DATE1__TODAY_NEW_RECOVERED)

            }
        }
    }

    companion object {
        const val FROM_DATE = "2021-10-02"
        const val TO_DATE = "2021-10-03"

        const val TOTAL_NAME = "Total"
        const val TOTAL_CONFIRMED = 4968012
        const val TOTAL_DEATHS = 87030

        const val COUNTRY1__ID = "afghanistan"
        const val COUNTRY1__NAME = "Afghanistan"
        const val COUNTRY1_DATE1 = FROM_DATE
        const val COUNTRY1_DATE1__TODAY_CONFIRMED = 5421
        const val COUNTRY1_DATE1__TODAY_DEATHS = 452
        const val COUNTRY1_DATE1__TODAY_NEW_CONFIRMED = 48
        const val COUNTRY1_DATE1__TODAY_NEW_DEATHS = 5
        const val COUNTRY1_DATE1__TODAY_NEW_OPEN_CASES = 10
        const val COUNTRY1_DATE1__TODAY_NEW_RECOVERED = 4
        const val COUNTRY1_DATE2 = TO_DATE
        const val COUNTRY1_DATE2__TODAY_CONFIRMED = 5631
        const val COUNTRY1_DATE2__TODAY_DEATHS = 460
        const val COUNTRY1_DATE2__TODAY_NEW_CONFIRMED = 210
        const val COUNTRY1_DATE2__TODAY_NEW_DEATHS = 8
        const val COUNTRY1_DATE2__TODAY_NEW_OPEN_CASES = 12
        const val COUNTRY1_DATE2__TODAY_NEW_RECOVERED = 3

        const val COUNTRY2__ID = "albania"
        const val COUNTRY2__NAME = "Albania"
        const val COUNTRY2_DATE1 = FROM_DATE
        const val COUNTRY2_DATE1__TODAY_CONFIRMED = 250
        const val COUNTRY2_DATE1__TODAY_DEATHS = 56
        const val COUNTRY2_DATE1__TODAY_NEW_CONFIRMED = 6
        const val COUNTRY2_DATE1__TODAY_NEW_DEATHS = 1
        const val COUNTRY2_DATE1__TODAY_NEW_OPEN_CASES = 6
        const val COUNTRY2_DATE1__TODAY_NEW_RECOVERED = 4
        const val COUNTRY2_DATE2 = TO_DATE
        const val COUNTRY2_DATE2__TODAY_CONFIRMED = 253
        const val COUNTRY2_DATE2__TODAY_DEATHS = 57
        const val COUNTRY2_DATE2__TODAY_NEW_CONFIRMED = 3
        const val COUNTRY2_DATE2__TODAY_NEW_DEATHS = 1
        const val COUNTRY2_DATE2__TODAY_NEW_OPEN_CASES = 2
        const val COUNTRY2_DATE2__TODAY_NEW_RECOVERED = 1

        const val SPAIN__ID = "spain"
        const val SPAIN__NAME = "Spain"
        const val SPAIN_DATE1 = FROM_DATE
        const val SPAIN_DATE1__TODAY_CONFIRMED = 4961128
        const val SPAIN_DATE1__TODAY_DEATHS = 86463
        const val SPAIN_DATE1__TODAY_NEW_CONFIRMED = 2037
        const val SPAIN_DATE1__TODAY_NEW_DEATHS = 48
        const val SPAIN_DATE1__TODAY_NEW_OPEN_CASES = 1989
        const val SPAIN_DATE1__TODAY_NEW_RECOVERED = 0
        const val SPAIN_DATE2 = TO_DATE
        const val SPAIN_DATE2__TODAY_CONFIRMED = 4962128
        const val SPAIN_DATE2__TODAY_DEATHS = 86513
        const val SPAIN_DATE2__TODAY_NEW_CONFIRMED = 1000
        const val SPAIN_DATE2__TODAY_NEW_DEATHS = 50
        const val SPAIN_DATE2__TODAY_NEW_OPEN_CASES = 2010
        const val SPAIN_DATE2__TODAY_NEW_RECOVERED = 2
    }
}