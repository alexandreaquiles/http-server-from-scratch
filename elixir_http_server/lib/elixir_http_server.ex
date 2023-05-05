defmodule ElixirHttpServer do
  @moduledoc """
  Documentation for `ElixirHttpServer`.
  """

  def start(port) do
    with  {:ok, server} <- :gen_tcp.listen(port, [:binary, active: false, backlog: 1000]) do
      server_loop(server)

      # close open sockets
      :gen_tcp.close(server)
    else
      {:error, reason} ->
        IO.inspect(reason)
    end
  end

  defp server_loop(server) do
    {:ok, client} = :gen_tcp.accept(server)
    RequestHandler.run(client)

    server_loop(server)
  end
end
