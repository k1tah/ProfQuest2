package com.example.profquest2.utils

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.navigation.NavType
import com.example.profquest2.R
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

val questions = listOf(
    Question(
        title = "Мне хотелось бы в своей профессиональной деятельности:",
        variants = listOf("Общаться с самыми разными людьми","Что-нибудь делать своими руками – мебель, одежду, машины и т.д.","Снимать фильмы, рисовать, писать книги, выступать на сцене и т.д."),
        image = R.drawable.quest1
    ),
    Question(
        title = "В книге или кинофильме меня больше всего привлекает:",
        variants = listOf("Художественная форма, мастерство писателя или режиссера", "Сюжет, действие героев", "Информация, которая может пригодиться в жизни"),
        image = R.drawable.quest2
    ),
    Question(
        title = "Меня обрадует Нобелевская премия:",
        variants = listOf("В области науки", "За общественную деятельность", "В области искусства"),
        image = R.drawable.quest3
    ),
    Question(
        title = "Я скорее соглашусь стать:",
        variants = listOf("Управляющим банка", "Главным инженером на производстве", "Начальником экспедиции"),
        image = R.drawable.quest4
    ),
    Question(
        title = "Будущее людей определяет",
        variants = listOf("Достижение науки", "Развитие производства", "Взаимопонимание среди людей"),
        image = R.drawable.quest5
    ),
    Question(
        title = "На месте директора школы я прежде всего займусь:",
        variants = listOf("Благоустройством школы (столовая, спортзал, компьютеры)", "Созданием дружного, сплоченного коллектива", "Разработкой новых технологий обучения"),
        image = R.drawable.quest6
    ),
    Question(
        title = "На технической выставке меня больше всего привлечет:",
        variants = listOf("Внешний вид экспонатов (цвет, форма)", "Внутреннее устройство экспонатов (механизм)", "Практическое применение экспонатов"),
        image = R.drawable.quest7
    ),
    Question(
        title = "В людях я ценю прежде всего:",
        variants = listOf("Мужество, смелость, выносливость", "Дружелюбие, чуткость, отзывчивость", "Ответственность, аккуратность"),
        image = R.drawable.quest8
    ),
    Question(
        title = "В свободное от работы время я буду:",
        variants = listOf("Писать стихи или музыку или рисовать", "Ставить различные опыты", "Тренироваться"),
        image = R.drawable.quest9
    ),
    Question(
        title = "В заграничных поездках меня больше всего привлечет:",
        variants = listOf("Экстремальный туризм (альпинизм, виндсерфинг, горные лыжи)", "Деловое общение", "Возможность знакомства с историей и культурой другой страны"),
        image = R.drawable.quest10
    ),
    Question(
        title = "Мне интереснее беседовать:",
        variants = listOf("О машине нового типа", "О новой научной теории", "О человеческих взаимоотношениях"),
        image = R.drawable.quest11
    ),
    Question(
        title = "Если бы в моей школе было всего три кружка, я бы выбрал:",
        variants = listOf("Технический", "Музыкальный", "Спортивный"),
        image = R.drawable.quest12
    ),
    Question(
        title = "В школе больше внимания следует уделять:",
        variants = listOf("Улучшению взаимопонимания между учителями и учениками", "Поддержанию здоровья учащихся, занятиям спортом", "Укреплению дисциплины"),
        image = R.drawable.quest13
    ),
    Question(
        title = "Я с большим интересом смотрю:",
        variants = listOf("Научно-популярные фильмы", "Программы о культуре и спорте", "Спортивные программы"),
        image = R.drawable.quest14
    ),
    Question(
        title = "Мне было бы интереснее работать:",
        variants = listOf("С машинами, механизмами", "С объектами природы", "С детьми или сверстниками"),
        image = R.drawable.quest15
    ),
    Question(
        title = "Школа в первую очередь должна:",
        variants = listOf("Давать знания и умения", "Учить общению с другими людьми", "Обучать навыкам работы"),
        image = R.drawable.quest16
    ),
    Question(
        title = "Каждый человек должен:",
        variants = listOf("Вести здоровый образ жизни", "Иметь возможность заниматься творчеством", "Иметь удобные бытовые условия"),
        image = R.drawable.quest17
    ),
    Question(
        title = "Для благополучия общества в первую очередь необходима:",
        variants = listOf("Защита интересов и прав граждан", "Забота о материальном благополучии людей", "Наука и технический прогресс"),
        image = R.drawable.quest18
    ),
    Question(
        title = "Мне больше всего нравятся уроки",
        variants = listOf("Физкультуры", "Математики", "Труда"),
        image = R.drawable.quest19
    ),
    Question(
        title = "Мне интереснее было бы:",
        variants = listOf("Планировать производство продукции", "Изготавливать изделия", "Заниматься сбытом продукции"),
        image = R.drawable.quest20
    ),
    Question(
        title = "Я предпочитаю читать статьи:",
        variants = listOf("О выдающихся ученых и их открытиях", "О творчестве ученых и музыкантов", "Об интересных изобретениях"),
        image = R.drawable.quest21
    ),
    Question(
        title = "Свободное время я охотнее провожу:",
        variants = listOf("Делая что-то по хозяйству", "С книгой", "На выставках, концертах и пр."),
        image = R.drawable.quest22
    ),
    Question(
        title = "Больший интерес у меня вызовет сообщение:",
        variants = listOf("О художественной выставке", "О ситуации на фондовой бирже", "О научном открытии"),
        image = R.drawable.quest23
    ),
    Question(
        title = "Я предпочитаю работать:",
        variants = listOf("В помещении, где много людей", "В необычных условиях", "В обычном кабинете"),
        image = R.drawable.quest24
    )
)

val social2TypeKeys = mapOf(
    0 to 1,
    2 to 2,
    4 to 3,
    5 to 2,
    7 to 2,
    10 to 3,
    12 to 1,
    14 to 3,
    15 to 2,
    17 to 1,
    19 to 3,
    23 to 1
)

val scienceTypeKeys = mapOf(
    1 to 3,
    2 to 1,
    4 to 1,
    5 to 3,
    6 to 2,
    8 to 2,
    10 to 2,
    13 to 1,
    17 to 3,
    20 to 1,
    21 to 2,
    22 to 3
)

val productionTypeKeys = mapOf(
    3 to 2,
    4 to 2,
    6 to 3,
    10 to 1,
    11 to 1,
    14 to 1,
    15 to 3,
    17 to 2,
    18 to 3,
    19 to 2,
    20 to 3,
    21 to 1
)

val aestheticTypeKeys = mapOf(
    0 to 3,
    1 to 1,
    2 to 3,
    6 to 1,
    8 to 1,
    9 to 3,
    11 to 2,
    13 to 2,
    16 to 2,
    20 to 2,
    21 to 3,
    22 to 1
)

val extremeTypeKeys = mapOf(
    1 to 2,
    3 to 3,
    7 to 1,
    8 to 3,
    9 to 1,
    11 to 3,
    12 to 2,
    13 to 3,
    14 to 2,
    16 to 1,
    18 to 1,
    23 to 2
)

val economicTypeKeys = mapOf(
    0 to 2,
    3 to 1,
    5 to 1,
    7 to 3,
    9 to 2,
    12 to 3,
    15 to 1,
    16 to 3,
    18 to 2,
    19 to 1,
    22 to 2,
    23 to 3
)

@Parcelize
enum class SecondTestResults(val title: String, val description: String) : Parcelable {
    Social(
        title = "Склонность к работе с людьми.",
        description = "Профессии, связанные с обслуживанием (бытовым, медицинским, информационным), управлением, воспитанием и обучением. Люди, успешные в профессиях этой группы, должны уметь и любить общаться, находить общий язык с разными людьми, понимать их настроение, намерения и особенности."
    ),
    Science(
        title = "Склонность к исследовательской деятельности.",
        description = "Профессии, связанные с научной работой. Кроме хорошей теоретической подготовки в определенных областях науки, людям, занимающимся исследовательской деятельностью, необходимы такие качества, как рациональность, независимость и оригинальность суждений, аналитический склад ума. Как правило, им больше нравится размышлять о проблеме, чем заниматься ее реализацией."
    ),
    Production(
        title = "Склонность к работе на производстве.",
        description = "Круг этих профессий очень широк: производство и обработка металла; сборка, монтаж приборов и механизмов; ремонт, наладка, обслуживание электронного и механического оборудования; монтаж, ремонт зданий, конструкций; обработка и использование различных материалов; управление транспортом. Профессии этой группы предъявляют повышенные требования к здоровью человека, координации движений, вниманию."
    ),
    Aesthetic(
        title = "Склонность к эстетическим видам деятельности.",
        description = "Профессии творческого характера, связанные с изобразительной, музыкальной, литературно-художественной, актерско-сценической деятельностью. Людей творческих профессий, кроме наличия специальных способностей (музыкальных, литературных, актерских), отличает оригинальность мышления и независимость характера, стремление к совершенству."
    ),
    Extreme(
        title = "Склонность к экстремальным видам деятельности.",
        description = "Профессии, связанные с занятиями спортом, путешествиями, экспедиционной работой, охранной и оперативно-розыскной деятельностью, службой в армии. Все они предъявляют особые требования к физической подготовке, здоровью и морально-волевым качествам."
    ),
    Economic(
        title = "Склонность к планово-экономическим видам деятельности.",
        description = "Профессии, связанные с расчетами и планированием (бухгалтер, экономист); делопроизводством, анализом и преобразованием текстов (редактор, переводчик, лингвист); схематическим изображением объектов (чертежник, топограф). Эти профессии требуют от человека собранности и аккуратности."
    )
}

data class Question(
    val title: String,
    val variants: List<String>,
    @DrawableRes val image: Int
)

class SecondTestResultsNavType : NavType<SecondTestResults>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): SecondTestResults? = bundle.getParcelable(key)

    override fun parseValue(value: String): SecondTestResults =
        Gson().fromJson(value, SecondTestResults::class.java)

    override fun put(bundle: Bundle, key: String, value: SecondTestResults) =
        bundle.putParcelable(key, value)
}

fun getSecondTestResult(answers: List<Int>): SecondTestResults {
    var social = 0
    var science = 0
    var production = 0
    var aesthetic = 0
    var extreme = 0
    var economic = 0
    answers.forEachIndexed { index, answer ->
        if (social2TypeKeys[index] == answer) social++
        if (scienceTypeKeys[index] == answer) science++
        if (productionTypeKeys[index] == answer) production++
        if (aestheticTypeKeys[index] == answer) aesthetic++
        if (extremeTypeKeys[index] == answer) extreme++
        if (economicTypeKeys[index] == answer) economic++
    }
    return when (maxOf(social, science, production, aesthetic, extreme, economic)) {
        social -> SecondTestResults.Social

        science -> SecondTestResults.Science

        production -> SecondTestResults.Production

        aesthetic -> SecondTestResults.Aesthetic

        extreme -> SecondTestResults.Extreme

        else -> SecondTestResults.Economic
    }
}