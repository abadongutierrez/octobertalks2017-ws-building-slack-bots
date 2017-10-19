defmodule Fortunebot.Bot do

  def auth(code) do
    "https://slack.com/api/oauth.access" <> 
    "?code=#{code}&client_id=#{System.get_env("CLIENT_ID")}&client_secret=#{System.get_env("CLIENT_SECRET")}"
    |> HTTPoison.get
    |> handle_oauth_access_response
  end

  defp handle_oauth_access_response({:ok, %HTTPoison.Response{body: body}}) do
    case Poison.Parser.parse(body, keys: :atoms) do
      {:ok, %{ok: true} = json} -> {:ok, json}
      {:ok, %{ok: false, error: reason}} -> {:error, reason}
      {:error, _} -> {:error, "Error parsing body"}
    end
  end

  defp handle_oauth_access_response(error), do: error

  def process_event(%{"type" => "message", "text" => text, "channel" => channel, "user" => user}) do
    bot_auth_info = Fortunebot.LocalDb.get_bot_auth_info
    if bot_auth_info != nil && !bot_user?(bot_auth_info, user) do
      if String.contains?(text, "fortune") do
        tell_fortune(bot_auth_info, channel)
      else
        post_message(bot_auth_info, channel, text)
      end
    end
    :ok
  end

  def process_event(_event), do: :ok

  defp bot_user?(bot_auth_info, user) do
    empty?(user) or bot_auth_info["bot_user_id"] == user
  end

  defp empty?(string) do
    string == nil or String.length(string) == 0
  end

  defp post_message(bot_auth_info, channel, text) do 
    "https://slack.com/api/chat.postMessage"
    |> HTTPoison.post({:form, [token: bot_auth_info.bot_access_token, channel: channel, text: text]},
                      %{"Content-type" => "application/x-www-form-urlencoded"})
  end

  defp tell_fortune(bot_auth_info, channel) do
    fortune_response = "https://helloacm.com/api/fortune/" |> HTTPoison.get
    case fortune_response do
      {:ok, %HTTPoison.Response{body: body}} ->
        post_message(bot_auth_info, channel, Macro.unescape_string(String.slice(body, 1..-2)))
    end
  end
end