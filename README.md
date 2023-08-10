## Vídeo

<details>
<summary>Post de criação de vídeo</summary>
  
##### Mapeamento:
```ruby
/api/video/criar
```
##### Parâmetros/Headers:
```ruby
@ModelAttribute VideoDTO videoDTO,
@RequestHeader("usuarioId") String usuarioId

VideoDTO(String titulo,
         String descricao,
         List<String> tags,
         String categoria,
         Boolean shorts,
         MultipartFile video,
         MultipartFile miniatura,
         Boolean restrito)
         
Todos os parâmetros do VideoDTO devem ser enviadas em um FormData

```
##### Observações:
Extensão da imagem: png </br>
Extensão do vídeo: mp4
</details>

<details>
<summary>Get de um vídeo completo</summary>
  
##### Mapeamento:
```ruby
/api/video/buscar-completo/{uuid}
```

##### Parâmetros/Headers:
```ruby
@RequestHeader("usuarioId") String usuarioId
```

</details>

<details>
<summary>Get de uma lista de vídeos resumidos</summary>
  
##### Mapeamento:
```ruby
/api/video/buscar-resumido
```
#####  Parâmetros/Headers:
```ruby
@RequestParam("size") int size,
@RequestParam("page") int page
```

</details>

<details>
<summary>Get de uma lista de vídeos resumidos por categoria</summary>
  
##### Mapeamento:
```ruby
/api/video/buscar-por-categoria
```
#####  Parâmetros/Headers:
```ruby
@ModelAttribute Categoria categoria,
@RequestParam("size") int size,
@RequestParam("page") int page
```

</details>

<details>
<summary>Buscar um CapiShorts</summary>

##### Mapeamento:
```ruby
/api/video/buscar-reels
```

##### Parâmetros/Headers:
```ruby
@RequestHeader("usuarioId") String usuarioId
```

</details>

<details>
<summary>Filtrar Vídeos</summary>

##### Mapeamento:
```ruby
/api/video/filtro/{pesquisa}
```

##### Parâmetros/Headers:
```ruby
@ModelAttribute FiltroDTO filtroDTO,
@RequestParam("page") int page,
@RequestParam("size") int size
```

</details>

## Pesquisa

<details>
<summary>Get de uma lista de vídeos resumidos de acordo com a pesquisa</summary>
  
##### Mapeamento:
```ruby
/api/pesquisa/{pesquisa}
```

##### Parâmetros/Headers:
```ruby
@RequestHeader("usuarioId") String usuarioId
```

</details>

<details>
<summary>Get histórico de pesquisa do usuário</summary>

##### Mapeamento:
```ruby
/api/usuario/historico-pesquisa
```

##### Parâmetros/Headers:
```ruby
@RequestHeader("usuarioId") String usuarioId
```

</details>

## Imagens

<details>

<summary>Como pegar uma imagem</summary>

Quando é feita uma requisição para vídeo, é retornado o campo _Caminhos_. Para colocar uma imagem ou um vídeo, basta pegar um link e coloca-lo na seguinte URL:
```ruby
http://localhost:7000/api/video/static/{caminho}
```
</details>
