package com.example.profquest2.utils

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.navigation.NavType
import com.example.profquest2.R
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

val realisticTypeKeys = mapOf(
    0 to 0,
    1 to 0,
    2 to 0,
    3 to 0,
    4 to 0,
    15 to 0,
    16 to 0,
    17 to 0,
    18 to 0,
    20 to 0,
    30 to 0,
    31 to 0,
    32 to 0,
    33 to 0
)

val intellectualTypeKeys = mapOf(
    0 to 1,
    5 to 0,
    6 to 0,
    7 to 0,
    8 to 0,
    15 to 1,
    19 to 0,
    21 to 0,
    22 to 0,
    23 to 0,
    30 to 1,
    34 to 0,
    35 to 0,
    36 to 0
)

val socialTypeKeys = mapOf(
    1 to 1,
    5 to 1,
    9 to 0,
    10 to 0,
    11 to 0,
    16 to 1,
    24 to 0,
    25 to 0,
    26 to 0,
    28 to 1,
    35 to 1,
    37 to 0,
    38 to 0,
    40 to 1
)

val conventionalTypeKeys = mapOf(
    2 to 1,
    6 to 1,
    9 to 1,
    12 to 0,
    13 to 0,
    17 to 1,
    21 to 1,
    24 to 1,
    27 to 0,
    28 to 0,
    31 to 1,
    37 to 1,
    39 to 0,
    41 to 0
)

val enterprisingTypeKeys = mapOf(
    3 to 1,
    7 to 1,
    10 to 1,
    12 to 0,
    14 to 0,
    22 to 1,
    27 to 1,
    29 to 1,
    32 to 0,
    34 to 0,
    36 to 1,
    38 to 1,
    39 to 0
)

val artisticTypeKeys = mapOf(
    4 to 1,
    8 to 1,
    11 to 1,
    13 to 0,
    14 to 0,
    18 to 1,
    20 to 1,
    23 to 1,
    26 to 0,
    28 to 0,
    29 to 1,
    33 to 1,
    40 to 0,
    41 to 0
)

@Parcelize
enum class Results(
    val type: String,
    val description: String,
    val specs: String,
    val orientation: String,
    val professionalEnvironment: String,
    val professions: String,
    val images: List<Int>
) : Parcelable {
    Realistic(
        type = "Реалистический тип",
        description = "Реалистический тип – личность, отличающаяся энергичным, агрессивным поведением, необщительная и стремящаяся к занятию конкретными материальными объектами. Обладает моторной ловкостью и математическими способностями.",
        specs = "Активность, агрессивность, деловитость, настойчивость, рациональность, практическое мышление, развитые двигательные навыки, пространственное воображение, технические способности",
        orientation = "Конкретный результат, настоящее, вещи, предметы и их практическое использование, занятия, требующие физического развития, ловкости, отсутствие ориентации на общение",
        professionalEnvironment = "Техника, сельское хозяйство, военное дело. Решение конкретных задач, требующих подвижности, двигательных умений, физической силы. Социальные навыки нужны в минимальной мере и связаны с приемом – передачей ограниченной информации.",
        professions = "Мeханик, электрик, инженер, фермер, зоотехник, агроном, садовод, автослесарь, шофер и т.д.",
        images = listOf(R.drawable.sadovnik, R.drawable.injener_elektrik, R.drawable.agronom)
    ),
    Intellectual(
        type = "Интеллектуальный тип",
        description = "Люди такого типа отличаются оригинальными аналитическими суждениями, они независимы от мнения окружающих, всегда имеют свою точку зрения. Часто выбирают для себя научную деятельность и преуспевают в ней. В частности, предпочитают заниматься географией, геологией, творческими профессиями, но и математиков среди интеллектуального типа немало. Эстетические ценности для таких людей всегда стоят выше, чем практические.",
        specs = "Аналитический ум, независимость и оригинальность суждений, гармоничное развитие языковых и математических способностей, критичность, любознательность, склонность к фантазии, интенсивная внутренняя жизнь, низкая физическая активность",
        orientation = "Идеи, теоретические ценности, умственный труд, решение интеллектуальных творческих задач, требующих абстрактного мышления, отсутствие ориентации на общение в деятельности, информационный характер общения",
        professionalEnvironment = "Наука. Решение задач, требующих абстрактного мышления и творческих способностей. Межличностные отношения играют незначительную роль, хотя необходимо уметь передавать и воспринимать сложные идеи",
        professions = "физик, астроном, ботаник, программист и др.",
        images = listOf(
            R.drawable.ycheni,
            R.drawable.specialis_po_jadernoi_phisic,
            R.drawable.arheolog
        )
    ),
    Social(
        type = "Социальный тип",
        description = "Обобщенное отражение, совокупность повторяющихся социальных качеств, присущих многим индивидам, входящим в какую-либо социальную общность. Например, европейский, азиатский, кавказский типы; студенты, рабочие, ветераны и др.",
        specs = "Умение общаться, гуманность, способность к сопереживанию, активность, зависимость от окружающих и общественного мнения, приспособление, решение проблем с опорой на эмоции и чувства, преобладание языковых способностей",
        orientation = "Люди, общение, установление контактов с окружающими, стремление учить, воспитывать, избегание интеллектуальных проблем ",
        professionalEnvironment = "Образование, здравоохранение, социальное обеспечение, обслуживание, спорт. Ситуации и проблемы, связанные с умением разбираться в поведении людей, требующие постоянного личного общения, умения убеждать.",
        professions = "врач, педагог, психолог и т.п.",
        images = listOf(R.drawable.vrach, R.drawable.ychitel, R.drawable.psiholog)
    ),
    Conventional(
        type = "Конвенциальный тип",
        description = "Этот тип отдает предпочтение четкому плану и алгоритму действий, пытается все структурировать и следовать инструкциям. Такие личности отлично справляются с рутинной работой. Правда, к решению вопросов они подходят шаблонно и стереотипно, подчиняются и зависимы от лидеров.",
        specs = "Способности к переработке числовой информации, стереотипный подход к проблемам, консервативный характер, подчиняемость, зависимость, следование обычаям, конформность, исполнительность, преобладание математических способностей",
        orientation = "Порядок, четко расписанная деятельность, работа по инструкции, заданным алгоритмам, избегание неопределенных ситуаций, социальной активности и физического напряжения, принятие позиции руководства",
        professionalEnvironment = "Экономика, связь, расчеты, бухгалтерия, делопроизводство. Деятельность, требующая способностей к обработке рутинной информации и числовых данных",
        professions = "бухгалтер, финансист, экономист, канцелярский служащий и др.",
        images = listOf(R.drawable.buchgalter, R.drawable.glav_buhgalter, R.drawable.economist)
    ),
    Enterprising(
        type = "Предприимчивый тип",
        description = "Амбициозные и оптимистичные люди, которые четко идут к своим целям и стремятся достичь рекордных результатов. Они считают себя лидерами, энергичными спикерами, умеющими влиять на мнение людей. Однако им не достает профессиональных знаний, они избегают исследовательских и аналитических задач.",
        specs = "Энергия, импульсивность, энтузиазм, предприимчивость, агрессивность, готовность к риску, оптимизм, уверенность в себе, преобладание языковых способностей, развитые организаторские способности",
        orientation = "Лидерство, признание, руководство, власть, личный статус, избегание занятий, требующих усидчивости, большого труда, двигательных навыков и концентрации внимания, интерес к экономике и политике",
        professionalEnvironment = "Решение неясных задач, общение с представителями различных типов в разнообразных ситуациях, требующих умения разбираться в мотивах поведения других людей и красноречия",
        professions = "бизнесмен, маркетолог, менеджер, директор, заведующий, журналист, репортер, дипломат, юрист, политик и т.д.",
        images = listOf(R.drawable.zav_magaz, R.drawable.director, R.drawable.diplomat)
    ),
    Artistic(
        type = "Артистический тип",
        description = "Такие люди обладают оригинальным, нетипичным мышлением, никого не слушают в принятии важных решений, опираются лишь на свою интуицию. Как правило, представители артистического типа не живут по правилам и традициям, они устанавливают свои правила и следуют только им.",
        specs = "Воображение и интуиция, эмоционально сложный взгляд на жизнь, независимость, гибкость и оригинальность мышления, развитые двигательные способности и восприятие",
        orientation = "Эмоции и чувства, самовыражение, творческие занятия, избегание деятельности, требующей физической силы, регламентированного рабочего времени, следования правилам и традициям",
        professionalEnvironment = "Изобразительное искусство, музыка, литература. Решение проблем, требующих художественного вкуса и воображения",
        professions = "музыкант, художник, фотограф, актер, режиссер, дизайнер и т.д.",
        images = listOf(R.drawable.designer, R.drawable.fotograph, R.drawable.acter)
    )
}

class TestResultsNavType : NavType<Results>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Results? = bundle.getParcelable(key)

    override fun parseValue(value: String): Results = Gson().fromJson(value, Results::class.java)

    override fun put(bundle: Bundle, key: String, value: Results) = bundle.putParcelable(key, value)
}

val listVariants = listOf(
    Pair(
        Profession(
            name = "Инженер-техник",
            image = R.drawable.ing_tech
        ),
        Profession(
            name = "Инженер-контролер",
            image = R.drawable.ing_cont
        )
    ),
    Pair(
        Profession(
            name = "Вязальщик",
            image = R.drawable.vazalshik
        ),
        Profession(
            name = "Санитарный врач",
            image = R.drawable.san_vrach
        )
    ),
    Pair(
        Profession(
            name = "Повар",
            image = R.drawable.povar
        ),
        Profession(
            name = "Наборщик",
            image = R.drawable.naborshik
        )
    ),
    Pair(
        Profession(
            name = "Фотограф",
            image = R.drawable.fotograph
        ),
        Profession(
            name = "Зав. магазином",
            image = R.drawable.zav_magaz
        )
    ),
    Pair(
        Profession(
            name = "Чертежник",
            image = R.drawable.chertezhnik
        ),
        Profession(
            name = "Дизайнер",
            image = R.drawable.designer
        )
    ),
    Pair(
        Profession(
            name = "Философ",
            image = R.drawable.filosof
        ),
        Profession(
            name = "Психиатр",
            image = R.drawable.psihiatr
        )
    ),
    Pair(
        Profession(
            name = "Ученый-химик",
            image = R.drawable.himik
        ),
        Profession(
            name = "Бухгалтер",
            image = R.drawable.buchgalter
        )
    ),
    Pair(
        Profession(
            name = "Редактор научного журнала",
            image = R.drawable.red_nauch
        ),
        Profession(
            name = "Адвокат",
            image = R.drawable.advocat
        )
    ),
    Pair(
        Profession(
            name = "Лингвист",
            image = R.drawable.lingvist
        ),
        Profession(
            name = "Переводчик художественной литературы",
            image = R.drawable.perevod_hudoj_literatur
        )
    ),
    Pair(
        Profession(
            name = "Педиатр",
            image = R.drawable.pediator
        ),
        Profession(
            name = "Статистик",
            image = R.drawable.satistik
        )
    ),
    Pair(
        Profession(
            name = "Организатор воспитательной работы",
            image = R.drawable.organizator_vospitalel_rabot
        ),
        Profession(
            name = "Председатель профсоюза",
            image = R.drawable.predsedatel_profsouza
        )
    ),
    Pair(
        Profession(
            name = "Спортивный врач",
            image = R.drawable.sport_vrach
        ),
        Profession(
            name = "Фельетонист",
            image = R.drawable.felietonist
        )
    ),
    Pair(
        Profession(
            name = "Нотариус",
            image = R.drawable.notarius
        ),
        Profession(
            name = "Снабженец",
            image = R.drawable.snabjenech
        )
    ),
    Pair(
        Profession(
            name = "Перфоратор",
            image = R.drawable.perforator
        ),
        Profession(
            name = "Карикатурист",
            image = R.drawable.karikaturist
        )
    ),
    Pair(
        Profession(
            name = "Политический деятель",
            image = R.drawable.polit_deyat
        ),
        Profession(
            name = "Писатель",
            image = R.drawable.pisatel
        )
    ),
    Pair(
        Profession(
            name = "Садовник",
            image = R.drawable.sadovnik
        ),
        Profession(
            name = "Метеоролог",
            image = R.drawable.meteorolog
        )
    ),
    Pair(
        Profession(
            name = "Водитель",
            image = R.drawable.voditel
        ),
        Profession(
            name = "Медсестра",
            image = R.drawable.medsestra
        )
    ),
    Pair(
        Profession(
            name = "Инженер-электрик",
            image = R.drawable.injener_elektrik
        ),
        Profession(
            name = "Секретарь-машинистка",
            image = R.drawable.sekretar_mashinistka
        )
    ),
    Pair(
        Profession(
            name = "Маляр",
            image = R.drawable.malyar
        ),
        Profession(
            name = "Художник по металлу",
            image = R.drawable.hudojnik_po_metallu
        )
    ),
    Pair(
        Profession(
            name = "Биолог",
            image = R.drawable.biolog
        ),
        Profession(
            name = "Главный врач",
            image = R.drawable.glav_vrach
        )
    ),
    Pair(
        Profession(
            name = "Телеоператор",
            image = R.drawable.teleoperator
        ),
        Profession(
            name = "Режиссер",
            image = R.drawable.rejisser
        )
    ),
    Pair(
        Profession(
            name = "Гидролог",
            image = R.drawable.gidrolog
        ),
        Profession(
            name = "Ревизор",
            image = R.drawable.revizor
        )
    ),
    Pair(
        Profession(
            name = "Зоолог",
            image = R.drawable.zoolog
        ),
        Profession(
            name = "Зоотехник",
            image = R.drawable.zootehnik
        )
    ),
    Pair(
        Profession(
            name = "Математик",
            image = R.drawable.matematik
        ),
        Profession(
            name = "Архитектор",
            image = R.drawable.arhitektor
        )
    ),
    Pair(
        Profession(
            name = "Работник ИДН",
            image = R.drawable.rabotnik_idn
        ),
        Profession(
            name = "Счетовод",
            image = R.drawable.chetovod
        )
    ),
    Pair(
        Profession(
            name = "Учитель",
            image = R.drawable.ychitel
        ),
        Profession(
            name = "Полицейский",
            image = R.drawable.militioner
        )
    ),
    Pair(
        Profession(
            name = "Воспитатель",
            image = R.drawable.vospitatel
        ),
        Profession(
            name = "Художник по керамике",
            image = R.drawable.hudojnik_po_keramike
        )
    ),
    Pair(
        Profession(
            name = "Экономист",
            image = R.drawable.economist
        ),
        Profession(
            name = "Заведующий отделом",
            image = R.drawable.zaveduchiy_otdelom
        )
    ),
    Pair(
        Profession(
            name = "Корректор",
            image = R.drawable.korrektor
        ),
        Profession(
            name = "Критик",
            image = R.drawable.kritik
        )
    ),
    Pair(
        Profession(
            name = "Завхоз",
            image = R.drawable.zavhoz
        ),
        Profession(
            name = "Директор",
            image = R.drawable.director
        )
    ),
    Pair(
        Profession(
            name = "Радиоинженер",
            image = R.drawable.radioinjener
        ),
        Profession(
            name = "Специалист по ядерной физике",
            image = R.drawable.specialis_po_jadernoi_phisic
        )
    ),
    Pair(
        Profession(
            name = "Водопроводчик",
            image = R.drawable.vodoprovodchik
        ),
        Profession(
            name = "Наборщик",
            image = R.drawable.naborshik
        )
    ),
    Pair(
        Profession(
            name = "Агроном",
            image = R.drawable.agronom
        ),
        Profession(
            name = "Председатель сельхозкооператива",
            image = R.drawable.predsetadel_selhozkooperativa
        )
    ),
    Pair(
        Profession(
            name = "Закройщик-модельер",
            image = R.drawable.zakrochik_modeller
        ),
        Profession(
            name = "Декоратор",
            image = R.drawable.dekorator
        )
    ),
    Pair(
        Profession(
            name = "Археолог",
            image = R.drawable.arheolog
        ),
        Profession(
            name = "Эксперт",
            image = R.drawable.expert
        )
    ),
    Pair(
        Profession(
            name = "Работник музея",
            image = R.drawable.rabotnik_museum
        ),
        Profession(
            name = "Консультант",
            image = R.drawable.kosultant
        )
    ),
    Pair(
        Profession(
            name = "Ученый",
            image = R.drawable.ycheni
        ),
        Profession(
            name = "Актер",
            image = R.drawable.acter
        )
    ),
    Pair(
        Profession(
            name = "Логопед",
            image = R.drawable.logoped
        ),
        Profession(
            name = "Стенографист",
            image = R.drawable.stenographist
        )
    ),
    Pair(
        Profession(
            name = "Врач",
            image = R.drawable.vrach
        ),
        Profession(
            name = "Дипломат",
            image = R.drawable.diplomat
        )
    ),
    Pair(
        Profession(
            name = "Главный бухгалтер",
            image = R.drawable.glav_buhgalter
        ),
        Profession(
            name = "Директор",
            image = R.drawable.director
        )
    ),
    Pair(
        Profession(
            name = "Поэт",
            image = R.drawable.poet
        ),
        Profession(
            name = "Психолог",
            image = R.drawable.psiholog
        )
    ),
    Pair(
        Profession(
            name = "Архивариус",
            image = R.drawable.arhivist
        ),
        Profession(
            name = "Скульптор",
            image = R.drawable.skulptor
        )
    )
)

data class Profession(
    val name: String,
    @DrawableRes val image: Int
)

fun getTestResult(answers: List<Int>): Results {
    var realistic = 0
    var intellectual = 0
    var social = 0
    var conventional = 0
    var enterprising = 0
    var artistic = 0
    answers.forEachIndexed { index, answer ->
        if (realisticTypeKeys[index] == answer) realistic++
        if (intellectualTypeKeys[index] == answer) intellectual++
        if (conventionalTypeKeys[index] == answer) conventional++
        if (socialTypeKeys[index] == answer) social++
        if (enterprisingTypeKeys[index] == answer) enterprising++
        if (artisticTypeKeys[index] == answer) artistic++
    }
    return when (maxOf(realistic, intellectual, conventional, social, enterprising, artistic)) {
        realistic -> Results.Realistic

        intellectual -> Results.Intellectual

        conventional -> Results.Conventional

        social -> Results.Social

        enterprising -> Results.Enterprising

        else -> Results.Artistic
    }
}