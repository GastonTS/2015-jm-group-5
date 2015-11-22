
    create table CondicionDeSalud (
        DTYPE varchar(31) not null,
        condicionDeSaludId bigint not null auto_increment,
        primary key (condicionDeSaludId)
    )

    create table Condimentacion (
        condimentacionId bigint not null auto_increment,
        cantidad double precision not null,
        condimento varchar(255),
        primary key (condimentacionId)
    )

    create table Consulta (
        consultaId bigint not null auto_increment,
        filtro_filtroId bigint,
        usuario_usuarioId bigint,
        primary key (consultaId)
    )

    create table Consulta_Receta (
        Consulta_consultaId bigint not null,
        recetasConsultadas_recetaId bigint not null
    )

    create table DisgustosXUsuario (
        Usuario_usuarioId bigint not null,
        disgustosAlimenticios_ingredienteID bigint not null
    )

    create table Filtro (
        DTYPE varchar(31) not null,
        filtroId bigint not null auto_increment,
        criterio mediumblob,
        nombreCriterio varchar(255),
        subFiltro_filtroId bigint,
        primary key (filtroId)
    )

    create table Grupo (
        grupoId bigint not null auto_increment,
        primary key (grupoId)
    )

    create table Grupo_Ingrediente (
        Grupo_grupoId bigint not null,
        preferenciasAlimenticias_ingredienteID bigint not null
    )

    create table Grupo_Usuario (
        Grupo_grupoId bigint not null,
        integrantes_usuarioId bigint not null
    )

    create table HistorialConsultaUsuario (
        id bigint not null auto_increment,
        consultor_usuarioId bigint,
        primary key (id)
    )

    create table HistorialConsultaUsuario_Receta (
        HistorialConsultaUsuario_id bigint not null,
        consultadas_recetaId bigint not null,
        consultadas_ORDER integer not null,
        primary key (HistorialConsultaUsuario_id, consultadas_ORDER)
    )

    create table Ingrediente (
        ingredienteID bigint not null auto_increment,
        nombre varchar(255),
        primary key (ingredienteID)
    )

    create table ObservadorConsultas (
        DTYPE varchar(31) not null,
        observadorID bigint not null auto_increment,
        primary key (observadorID)
    )

    create table ObservadorConsultas_HistorialConsultaUsuario (
        ObservadorConsultas_observadorID bigint not null,
        historialesConsultados_id bigint not null
    )

    create table ObservadorConsultas_Usuario (
        ObservadorConsultas_observadorID bigint not null,
        veganosQueConsultaronDificiles_usuarioId bigint not null
    )

    create table PorHoraDelDia_consultasPorHoraDelDia (
        PorHoraDelDia_observadorID bigint not null,
        consultasPorHoraDelDia integer,
        consultasPorHoraDelDia_KEY integer,
        primary key (PorHoraDelDia_observadorID, consultasPorHoraDelDia_KEY)
    )

    create table PreferenciasXUsuario (
        Usuario_usuarioId bigint not null,
        preferenciasAlimenticias_ingredienteID bigint not null
    )

    create table ProcesoAsincronico (
        DTYPE varchar(31) not null,
        ProcesoAsincronicoId bigint not null auto_increment,
        primary key (ProcesoAsincronicoId)
    )

    create table ProcesoAsincronico_Usuario (
        ProcesoAsincronico_ProcesoAsincronicoId bigint not null,
        usuariosPorLosQueSeMandanMail_usuarioId bigint not null
    )

    create table Receta (
        recetaId bigint not null auto_increment,
        cantCalorias double precision not null,
        dificultad integer,
        nombre varchar(255),
        preparacion varchar(255),
        temporada integer,
        urlImagen varchar(255),
        dueño_usuarioId bigint,
        primary key (recetaId)
    )

    create table Receta_Condimentacion (
        Receta_recetaId bigint not null,
        condimentaciones_condimentacionId bigint not null
    )

    create table Receta_Ingrediente (
        Receta_recetaId bigint not null,
        ingredientes_ingredienteID bigint not null
    )

    create table Receta_Receta (
        Receta_recetaId bigint not null,
        subRecetas_recetaId bigint not null
    )

    create table RecetasHombresXAcumulador (
        ObservadorConsultas_observadorID bigint not null,
        consultasHombres_recetaId bigint not null
    )

    create table RecetasMujeresXAcumulador (
        ObservadorConsultas_observadorID bigint not null,
        consultasMujeres_recetaId bigint not null
    )

    create table RecetasTotalesXAcumulador (
        ObservadorConsultas_observadorID bigint not null,
        recetasConsultadas_recetaId bigint not null
    )

    create table Usuario (
        usuarioId bigint not null auto_increment,
        aceptado bit,
        estatura double precision not null,
        peso double precision not null,
        fechaDeNacimiento tinyblob,
        nombre varchar(255),
        sexo integer,
        mail varchar(255),
        rutina integer,
        primary key (usuarioId)
    )

    create table Usuario_CondicionDeSalud (
        Usuario_usuarioId bigint not null,
        condicionesDeSalud_condicionDeSaludId bigint not null
    )

    create table Usuario_Grupo (
        Usuario_usuarioId bigint not null,
        grupos_grupoId bigint not null
    )

    create table Usuario_Receta (
        Usuario_usuarioId bigint not null,
        recetasFavoritas_recetaId bigint not null
    )

    alter table ObservadorConsultas_HistorialConsultaUsuario 
        add constraint UK_prgftrly7pbt674863gl557a7  unique (historialesConsultados_id)

    alter table Consulta 
        add constraint FK_7j833nmdmrs2l5p9egygej3l3 
        foreign key (filtro_filtroId) 
        references Filtro (filtroId)

    alter table Consulta 
        add constraint FK_61nwb3acg75ypb58od7gdndpf 
        foreign key (usuario_usuarioId) 
        references Usuario (usuarioId)

    alter table Consulta_Receta 
        add constraint FK_h9eloxne15qpasglrgwsn9xkh 
        foreign key (recetasConsultadas_recetaId) 
        references Receta (recetaId)

    alter table Consulta_Receta 
        add constraint FK_lo9fwq4tla0jf45b5plna3xyd 
        foreign key (Consulta_consultaId) 
        references Consulta (consultaId)

    alter table DisgustosXUsuario 
        add constraint FK_521xgvyutkk2rsl8ql9014lts 
        foreign key (disgustosAlimenticios_ingredienteID) 
        references Ingrediente (ingredienteID)

    alter table DisgustosXUsuario 
        add constraint FK_hqescfnm9k80yl4aghsct9l2e 
        foreign key (Usuario_usuarioId) 
        references Usuario (usuarioId)

    alter table Filtro 
        add constraint FK_6so41xcadsj591syksodxy79q 
        foreign key (subFiltro_filtroId) 
        references Filtro (filtroId)

    alter table Grupo_Ingrediente 
        add constraint FK_sshpmv5d8f8ay782mhjd6ve9y 
        foreign key (preferenciasAlimenticias_ingredienteID) 
        references Ingrediente (ingredienteID)

    alter table Grupo_Ingrediente 
        add constraint FK_r5ydxgi94tdkqqv4b1eb4j38p 
        foreign key (Grupo_grupoId) 
        references Grupo (grupoId)

    alter table Grupo_Usuario 
        add constraint FK_4lyt1nja2x1qwxn3m03h92mas 
        foreign key (integrantes_usuarioId) 
        references Usuario (usuarioId)

    alter table Grupo_Usuario 
        add constraint FK_cnr4fodecgw0g48cqejhfle 
        foreign key (Grupo_grupoId) 
        references Grupo (grupoId)

    alter table HistorialConsultaUsuario 
        add constraint FK_7ohmr76locox3egvc3hwh1y8f 
        foreign key (consultor_usuarioId) 
        references Usuario (usuarioId)

    alter table HistorialConsultaUsuario_Receta 
        add constraint FK_fvmaid6k6fhj8ibi0ka57uoh4 
        foreign key (consultadas_recetaId) 
        references Receta (recetaId)

    alter table HistorialConsultaUsuario_Receta 
        add constraint FK_5pp9nm7fk9ifu2x2prde726h4 
        foreign key (HistorialConsultaUsuario_id) 
        references HistorialConsultaUsuario (id)

    alter table ObservadorConsultas_HistorialConsultaUsuario 
        add constraint FK_prgftrly7pbt674863gl557a7 
        foreign key (historialesConsultados_id) 
        references HistorialConsultaUsuario (id)

    alter table ObservadorConsultas_HistorialConsultaUsuario 
        add constraint FK_fm77sde87sl1m5ek4g9b80fk3 
        foreign key (ObservadorConsultas_observadorID) 
        references ObservadorConsultas (observadorID)

    alter table ObservadorConsultas_Usuario 
        add constraint FK_1q096hu0i6s8wxpnwawcipsih 
        foreign key (veganosQueConsultaronDificiles_usuarioId) 
        references Usuario (usuarioId)

    alter table ObservadorConsultas_Usuario 
        add constraint FK_68u32tvtmpldkcj4w4l9iqk0s 
        foreign key (ObservadorConsultas_observadorID) 
        references ObservadorConsultas (observadorID)

    alter table PorHoraDelDia_consultasPorHoraDelDia 
        add constraint FK_n549i58os9eor7jtoasiggxn8 
        foreign key (PorHoraDelDia_observadorID) 
        references ObservadorConsultas (observadorID)

    alter table PreferenciasXUsuario 
        add constraint FK_l159qsvli2e5kt4u6et0uasxb 
        foreign key (preferenciasAlimenticias_ingredienteID) 
        references Ingrediente (ingredienteID)

    alter table PreferenciasXUsuario 
        add constraint FK_ai4i2yi158r0o5e1sc6r20g4t 
        foreign key (Usuario_usuarioId) 
        references Usuario (usuarioId)

    alter table ProcesoAsincronico_Usuario 
        add constraint FK_i8t14k6m1ofe98e534cvma5vj 
        foreign key (usuariosPorLosQueSeMandanMail_usuarioId) 
        references Usuario (usuarioId)

    alter table ProcesoAsincronico_Usuario 
        add constraint FK_hadmpjymvc06i6ad16laggdv1 
        foreign key (ProcesoAsincronico_ProcesoAsincronicoId) 
        references ProcesoAsincronico (ProcesoAsincronicoId)

    alter table Receta 
        add constraint FK_dsghfxv4sttxijx8qcb0jw05p 
        foreign key (dueño_usuarioId) 
        references Usuario (usuarioId)

    alter table Receta_Condimentacion 
        add constraint FK_k1dae8vco2ewquk9lcpnyaetd 
        foreign key (condimentaciones_condimentacionId) 
        references Condimentacion (condimentacionId)

    alter table Receta_Condimentacion 
        add constraint FK_c7v9ks0bhmrjn9r3r507ra40p 
        foreign key (Receta_recetaId) 
        references Receta (recetaId)

    alter table Receta_Ingrediente 
        add constraint FK_ex0hkr2fji4fk2apoy1nvwex0 
        foreign key (ingredientes_ingredienteID) 
        references Ingrediente (ingredienteID)

    alter table Receta_Ingrediente 
        add constraint FK_1xuu24xduqtbya2206vdaq0o7 
        foreign key (Receta_recetaId) 
        references Receta (recetaId)

    alter table Receta_Receta 
        add constraint FK_f7c6f46mt97mwkgyudvsr7cvb 
        foreign key (subRecetas_recetaId) 
        references Receta (recetaId)

    alter table Receta_Receta 
        add constraint FK_9hjk8jycwmv8rg072nfjbvjnk 
        foreign key (Receta_recetaId) 
        references Receta (recetaId)

    alter table RecetasHombresXAcumulador 
        add constraint FK_bqjxn63uim3hurqkvy0iyscd 
        foreign key (consultasHombres_recetaId) 
        references Receta (recetaId)

    alter table RecetasHombresXAcumulador 
        add constraint FK_db554ur586psy9ii083t6np02 
        foreign key (ObservadorConsultas_observadorID) 
        references ObservadorConsultas (observadorID)

    alter table RecetasMujeresXAcumulador 
        add constraint FK_eupamtydgk6m1sxu3k5m8bbfb 
        foreign key (consultasMujeres_recetaId) 
        references Receta (recetaId)

    alter table RecetasMujeresXAcumulador 
        add constraint FK_eaqr9hs0w7te067hsv7ae7sex 
        foreign key (ObservadorConsultas_observadorID) 
        references ObservadorConsultas (observadorID)

    alter table RecetasTotalesXAcumulador 
        add constraint FK_nfx2ymi4un9khc3wl31h0qspr 
        foreign key (recetasConsultadas_recetaId) 
        references Receta (recetaId)

    alter table RecetasTotalesXAcumulador 
        add constraint FK_7rh7vyrmsy11oiwbvv1xasbbf 
        foreign key (ObservadorConsultas_observadorID) 
        references ObservadorConsultas (observadorID)

    alter table Usuario_CondicionDeSalud 
        add constraint FK_syq8y5qd52aw06i146n0nq9js 
        foreign key (condicionesDeSalud_condicionDeSaludId) 
        references CondicionDeSalud (condicionDeSaludId)

    alter table Usuario_CondicionDeSalud 
        add constraint FK_9651cks3x5394t1t6ljer4bo5 
        foreign key (Usuario_usuarioId) 
        references Usuario (usuarioId)

    alter table Usuario_Grupo 
        add constraint FK_gvd1q0uxcjaqoahx8osb638qu 
        foreign key (grupos_grupoId) 
        references Grupo (grupoId)

    alter table Usuario_Grupo 
        add constraint FK_fgpxgrf2qav6xmptkqrcsbiyo 
        foreign key (Usuario_usuarioId) 
        references Usuario (usuarioId)

    alter table Usuario_Receta 
        add constraint FK_aj2jvcfabcslpxa0t2e80bjaa 
        foreign key (recetasFavoritas_recetaId) 
        references Receta (recetaId)

    alter table Usuario_Receta 
        add constraint FK_m556bbwaw6qd70pnx5urmd1cr 
        foreign key (Usuario_usuarioId) 
        references Usuario (usuarioId)
